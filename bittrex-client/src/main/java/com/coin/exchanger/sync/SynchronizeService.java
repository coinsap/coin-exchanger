package com.coin.exchanger.sync;

import com.coin.exchanger.market.Market;
import com.coin.exchanger.market.MarketRepository;
import com.coin.exchanger.market.currency.Currency;
import com.coin.exchanger.market.currency.CurrencyRepository;
import com.coin.exchanger.market.history.MarketHistory;
import com.coin.exchanger.market.history.MarketHistoryRepository;
import com.coin.exchanger.market.order.Buy;
import com.coin.exchanger.market.order.BuyRepository;
import com.coin.exchanger.market.order.Sell;
import com.coin.exchanger.market.order.SellRepository;
import com.coin.exchanger.market.summary.MarketSummary;
import com.coin.exchanger.market.summary.MarketSummaryRepository;
import com.coin.exchanger.market.summary.ticker.Ticker;
import com.coin.exchanger.remote.response.*;
import com.coin.exchanger.remote.response.base.ResponseListWrapper;
import com.coin.exchanger.remote.response.base.ResponseWrapper;
import com.coin.exchanger.remote.service.RemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Objects;
import java.util.Set;

/**
 * @author Semih Beceren
 */
@Service
public class SynchronizeService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MarketRepository marketRepository;
    private final CurrencyRepository currencyRepository;
    private final RemoteService remoteService;
    private final MarketHistoryRepository marketHistoryRepository;
    private final MarketSummaryRepository marketSummaryRepository;
    private static Set<Market> MARKETS;
    private final BuyRepository buyRepository;
    private final SellRepository sellRepository;

    @Autowired
    public SynchronizeService(MarketRepository marketRepository, MarketHistoryRepository marketHistoryRepository, CurrencyRepository currencyRepository, RemoteService remoteService, MarketSummaryRepository marketSummaryRepository, BuyRepository buyRepository, SellRepository sellRepository) {
        this.marketRepository = marketRepository;
        this.marketHistoryRepository = marketHistoryRepository;
        this.currencyRepository = currencyRepository;
        this.remoteService = remoteService;
        this.marketSummaryRepository = marketSummaryRepository;
        this.buyRepository = buyRepository;
        this.sellRepository = sellRepository;
    }


    public void syncCurrencies() {
        ResponseListWrapper<CurrencyHolder> currencyHolderResponseListWrapper = remoteService.getCurrenciesRestCall();
        Set<String> currencies = currencyRepository.findAllCurrencyNames();
        if (isResponseSuccess(currencyHolderResponseListWrapper)) {
            logger.info("Start Currency Sync");
            logger.info("Currencies count on DB: {}", currencies.size());
            logger.info("Currencies count on Request: {}", currencyHolderResponseListWrapper.getResult().size());
            currencyHolderResponseListWrapper.getResult()
                    .stream()
                    .filter(currencyHolder -> !currencies.contains(currencyHolder.getCurrency()))
                    .map(currencyHolder -> new Currency(currencyHolder.getCurrency(), currencyHolder.getTxFee(), currencyHolder.getActive(), currencyHolder.getCoinType()))
                    .forEach(currencyRepository::save);
            logger.info("End Currency Sync");
        }
    }

    public void syncMarkets() {
        ResponseListWrapper<MarketHolder> marketHolderResponseListWrapper = remoteService.getMarketsRestCall();
        Set<String> markets = marketRepository.findAllMarketNames();
        if (isResponseSuccess(marketHolderResponseListWrapper)) {
            logger.info("Start Market Sync");
            logger.info("Markets count on DB: {}", markets.size());
            logger.info("Markets count on Request: {}", marketHolderResponseListWrapper.getResult().size());
            marketHolderResponseListWrapper.getResult()
                    .stream()
                    .limit(5)
                    .filter(marketHolder -> !markets.contains(marketHolder.getMarketName()))
                    .map(marketHolder -> new Market(currencyRepository.findByCurrency(marketHolder.getMarketCurrency()), currencyRepository.findByCurrency(marketHolder.getBaseCurrency()), marketHolder.getMarketName(), marketHolder.getMinTradeSize(), marketHolder.getActive()))
                    .forEach(marketRepository::save);
            logger.info("End Market Sync");
        }
        MARKETS = marketRepository.findAll();
    }

    public void syncMarketHistory() {
        logger.info("Start Market History Sync");
        MARKETS.forEach(market -> {
            ResponseListWrapper<MarketHistoryHolder> marketHistoryHolderResponseListWrapper = remoteService.getMarketHistoryRestCall(market.getMarketName());
            if (isResponseSuccess(marketHistoryHolderResponseListWrapper)) {
                marketHistoryHolderResponseListWrapper.getResult()
                        .stream()
                        .filter(marketHistoryHolder -> Objects.isNull(marketHistoryRepository.findByApiId(marketHistoryHolder.getId())))
                        .map(marketHistoryHolder -> new MarketHistory(market, marketHistoryHolder.getFillType(), marketHistoryHolder.getId(), marketHistoryHolder.getQuantity(), marketHistoryHolder.getPrice(), marketHistoryHolder.getTotal(), marketHistoryHolder.getOrderType(), marketHistoryHolder.getTimeStamp()))
                        .forEach(marketHistoryRepository::save);
            }
        });
        logger.info("End Market History Sync");
    }

    public void syncMarketSummary(){
        logger.info("Start Market Summary Sync");
        MARKETS.forEach(market -> {
            ResponseListWrapper<MarketSummaryHolder> marketSummaryHolderResponseListWrapper = remoteService.getMarketSummaryRestCall(market.getMarketName());
            if(isResponseSuccess(marketSummaryHolderResponseListWrapper)){
                marketSummaryHolderResponseListWrapper.getResult()
                        .stream()
                        .filter(marketSummaryHolder -> Objects.isNull(marketSummaryRepository.findByHash(DigestUtils.md5DigestAsHex((marketSummaryHolder.getMarketName() + marketSummaryHolder.getTimeStamp()).getBytes()))))
                        .map(marketSummaryHolder -> new MarketSummary(market, marketSummaryHolder.getHigh(), marketSummaryHolder.getLow(), marketSummaryHolder.getVolume(), marketSummaryHolder.getBaseVolume(), new Ticker(marketSummaryHolder.getBid(), marketSummaryHolder.getAsk(), marketSummaryHolder.getLast()), marketSummaryHolder.getTimeStamp(), marketSummaryHolder.getOpenBuyOrders(), marketSummaryHolder.getOpenSellOrders(), marketSummaryHolder.getPrevDay()))
                        .forEach(marketSummaryRepository::save);
            }
        });
        logger.info("End Market Summary Sync");
    }

    public void syncOrderBook(){
        Set<Market> markets = marketRepository.findAll();
        logger.info("Start Buy Book Sync");
        markets.forEach(market -> {
            ResponseWrapper<OrderBookHolder> orderBookHolderResponseWrapper = remoteService.getOrderBookRestCall(market.getMarketName());
            if(Objects.nonNull(orderBookHolderResponseWrapper.getResult()) && orderBookHolderResponseWrapper.getSuccess()){
                orderBookHolderResponseWrapper.getResult().getBuy()
                        .stream()
                        .filter(orderHolder -> Objects.isNull(buyRepository.findByHash(DigestUtils.md5DigestAsHex((market.getMarketName() + orderHolder.getQuantity().toString() + orderHolder.getRate().toString()).getBytes()))))
                        .map(orderHolder -> new Buy(orderHolder.getQuantity(), orderHolder.getRate(), market))
                        .forEach(buyRepository::save);

                orderBookHolderResponseWrapper.getResult().getSell()
                        .stream()
                        .filter(orderHolder -> Objects.isNull(sellRepository.findByHash(DigestUtils.md5DigestAsHex((market.getMarketName() + orderHolder.getQuantity().toString() + orderHolder.getRate().toString()).getBytes()))))
                        .map(orderHolder -> new Sell(orderHolder.getQuantity(), orderHolder.getRate(), market))
                        .forEach(sellRepository::save);
            }
        });
        logger.info("End Buy Book Sync");
    }

    private boolean isResponseSuccess(ResponseListWrapper<?> responseListWrapper) {
        return Objects.nonNull(responseListWrapper) && responseListWrapper.getSuccess() && Objects.nonNull(responseListWrapper.getResult());
    }


}
