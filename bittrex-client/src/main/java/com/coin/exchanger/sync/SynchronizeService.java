package com.coin.exchanger.sync;

import com.coin.exchanger.market.Market;
import com.coin.exchanger.market.MarketRepository;
import com.coin.exchanger.market.currency.Currency;
import com.coin.exchanger.market.currency.CurrencyRepository;
import com.coin.exchanger.remote.response.CurrencyHolder;
import com.coin.exchanger.remote.response.MarketHolder;
import com.coin.exchanger.remote.response.base.ResponseListWrapper;
import com.coin.exchanger.remote.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Semih Beceren
 */
@Service
public class SynchronizeService {

    private final MarketRepository marketRepository;
    private final CurrencyRepository currencyRepository;
    private final RemoteService remoteService;

    @Autowired
    public SynchronizeService(MarketRepository marketRepository, CurrencyRepository currencyRepository, RemoteService remoteService) {
        this.marketRepository = marketRepository;
        this.currencyRepository = currencyRepository;
        this.remoteService = remoteService;
    }


    public void syncCurrencies() {
        ResponseListWrapper<CurrencyHolder> currencyHolderResponseListWrapper = remoteService.getCurrenciesRestCall();
        Set<String> currencies = currencyRepository.findAllCurrencyNames();
        if (Objects.nonNull(currencyHolderResponseListWrapper) && currencyHolderResponseListWrapper.getSuccess() && Objects.nonNull(currencyHolderResponseListWrapper.getResult())) {
            currencyHolderResponseListWrapper.getResult()
                    .stream()
                    .filter(currencyHolder -> !currencies.contains(currencyHolder.getCurrency()))
                    .collect(Collectors.toList())
                    .stream()
                    .map(currencyHolder -> new Currency(currencyHolder.getCurrency(), currencyHolder.getTxFee(), currencyHolder.getActive(), currencyHolder.getCoinType()))
                    .forEach(currencyRepository::save);
        }
    }

    public void syncMarkets() {
        ResponseListWrapper<MarketHolder> marketHolderResponseListWrapper = remoteService.getMarketsRestCall();
        Set<String> markets = marketRepository.findAllMarketNames();
        if (Objects.nonNull(marketHolderResponseListWrapper) && marketHolderResponseListWrapper.getSuccess() && Objects.nonNull(marketHolderResponseListWrapper.getResult())) {
            marketHolderResponseListWrapper.getResult()
                    .stream()
                    .filter(marketHolder -> !markets.contains(marketHolder.getMarketName()))
                    .collect(Collectors.toList())
                    .stream()
                    .map(marketHolder -> new Market(currencyRepository.findByCurrency(marketHolder.getMarketCurrency()), currencyRepository.findByCurrency(marketHolder.getBaseCurrency()), marketHolder.getMarketName(), marketHolder.getMinTradeSize(), marketHolder.getActive(), (Date) DateTimeFormatter.ISO_DATE_TIME.parse(marketHolder.getCreated())))
                    .forEach(marketRepository::save);
        }
    }


}
