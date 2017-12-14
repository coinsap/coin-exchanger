package com.coin.exchanger.sync;

import com.coin.exchanger.market.Market;
import com.coin.exchanger.market.MarketRepository;
import com.coin.exchanger.market.currency.Currency;
import com.coin.exchanger.market.currency.CurrencyRepository;
import com.coin.exchanger.market.history.MarketHistory;
import com.coin.exchanger.market.history.MarketHistoryRepository;
import com.coin.exchanger.remote.response.CurrencyHolder;
import com.coin.exchanger.remote.response.MarketHistoryHolder;
import com.coin.exchanger.remote.response.MarketHolder;
import com.coin.exchanger.remote.response.base.ResponseListWrapper;
import com.coin.exchanger.remote.service.RemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    private static Set<Long> MARKET_HISTORY_HOLDER_ID_TEMPORAL_LIST = new HashSet<>();
    private static Map<String, Date> MARKET_SUMMARY_HOLDER_TEMPORAL_MAP = new HashMap<>();

    @Autowired
    public SynchronizeService(MarketRepository marketRepository, MarketHistoryRepository marketHistoryRepository, CurrencyRepository currencyRepository, RemoteService remoteService, MarketSummaryRepository marketSummaryRepository) {
        this.marketRepository = marketRepository;
        this.marketHistoryRepository = marketHistoryRepository;
        this.currencyRepository = currencyRepository;
        this.remoteService = remoteService;
        this.marketSummaryRepository = marketSummaryRepository;
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
                    .filter(marketHolder -> !markets.contains(marketHolder.getMarketName()))
                    .map(marketHolder -> new Market(currencyRepository.findByCurrency(marketHolder.getMarketCurrency()), currencyRepository.findByCurrency(marketHolder.getBaseCurrency()), marketHolder.getMarketName(), marketHolder.getMinTradeSize(), marketHolder.getActive(), marketHolder.getCreated()))
                    .forEach(marketRepository::save);
            logger.info("End Market Sync");
        }
    }

    public void syncMarketHistory() {
        Set<Market> markets = marketRepository.findAll();
        logger.info("Start Market History Sync");
        markets.forEach(market -> {
            ResponseListWrapper<MarketHistoryHolder> marketHistoryHolderResponseListWrapper = remoteService.getMarketHistoryRestCall(market.getMarketName());
            if (isResponseSuccess(marketHistoryHolderResponseListWrapper)) {
                marketHistoryHolderResponseListWrapper.getResult()
                        .stream()
                        .filter(marketHistoryHolder -> !MARKET_HISTORY_HOLDER_ID_TEMPORAL_LIST.contains(marketHistoryHolder.getId()))
                        .map(marketHistoryHolder -> new MarketHistory(market, marketHistoryHolder.getFillType(), marketHistoryHolder.getId(), marketHistoryHolder.getQuantity(), marketHistoryHolder.getPrice(), marketHistoryHolder.getTotal(), marketHistoryHolder.getOrderType(), marketHistoryHolder.getTimeStamp()))
                        .forEach(marketHistoryRepository::save);

                MARKET_HISTORY_HOLDER_ID_TEMPORAL_LIST = marketHistoryHolderResponseListWrapper.getResult().stream().map(MarketHistoryHolder::getId).collect(Collectors.toSet());
            }
        });
        logger.info("End Market History Sync");
    }

    public void syncMarketSummary(){
        Set<Market> markets = marketRepository.findAll();
        logger.info("Start Market Summary Sync");
        markets.forEach(market -> {
            ResponseListWrapper<MarketSummaryHolder> marketSummaryHolderResponseListWrapper = remoteService.getMarketSummaryRestCall(market.getMarketName());
            if(isResponseSuccess(marketSummaryHolderResponseListWrapper)){
                marketSummaryHolderResponseListWrapper.getResult()
                        .stream()
                        .filter(marketSummaryHolder -> !MARKET_SUMMARY_HOLDER_TEMPORAL_MAP.get(market.getMarketName()).equals(marketSummaryHolder.getTimeStamp()))
                        .map(marketSummaryHolder -> new MarketSummary(market, marketSummaryHolder.getHigh(), marketSummaryHolder.getLow(), marketSummaryHolder.getVolume(), marketSummaryHolder.getBaseVolume(), new Ticker(marketSummaryHolder.getBid(), marketSummaryHolder.getAsk(), marketSummaryHolder.getLast()), marketSummaryHolder.getTimeStamp(), marketSummaryHolder.getOpenBuyOrders(), marketSummaryHolder.getOpenSellOrders(), marketSummaryHolder.getPrevDay(), marketSummaryHolder.getCreated()))
                        .forEach(marketSummaryRepository::save);

                MARKET_SUMMARY_HOLDER_TEMPORAL_MAP = marketSummaryHolderResponseListWrapper.getResult()
                        .stream()
                        .collect(Collectors.toMap(MarketSummaryHolder::getMarketName, MarketSummaryHolder::getTimeStamp));

            }
        });
        logger.info("End Market Summary Sync");
    }

    public void syncOrderBook(){
        Set<Market> markets = marketRepository.findAll();
        logger.info("Start Order Book Sync");
        markets.forEach(market -> {
            ResponseWrapper<OrderBookHolder> orderBookHolderResponseWrapper = remoteService.getOrderBookRestCall(market.getMarketName());
            if(Objects.nonNull(orderBookHolderResponseWrapper.getResult()) && orderBookHolderResponseWrapper.getSuccess()){
                orderBookHolderResponseWrapper.getResult().getBuy().stream()
                        .map(orderHolder -> orderHolder.)
            }
        });
        logger.info("End Order Book Sync");
    }

    private boolean isResponseSuccess(ResponseListWrapper<?> responseListWrapper) {
        return Objects.nonNull(responseListWrapper) && responseListWrapper.getSuccess() && Objects.nonNull(responseListWrapper.getResult());
    }


}
