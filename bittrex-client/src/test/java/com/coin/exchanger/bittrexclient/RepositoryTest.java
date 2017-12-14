package com.coin.exchanger.bittrexclient;

import com.coin.exchanger.market.Market;
import com.coin.exchanger.market.MarketRepository;
import com.coin.exchanger.market.currency.Currency;
import com.coin.exchanger.market.currency.CurrencyRepository;
import com.coin.exchanger.market.history.FillType;
import com.coin.exchanger.market.history.MarketHistory;
import com.coin.exchanger.market.history.MarketHistoryRepository;
import com.coin.exchanger.market.summary.MarketSummary;
import com.coin.exchanger.market.summary.MarketSummaryRepository;
import com.coin.exchanger.market.summary.ticker.Ticker;
import com.coin.exchanger.market.summary.ticker.TickerRepository;
import com.coin.exchanger.remote.response.OrderType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author Semih Beceren
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private MarketHistoryRepository marketHistoryRepository;

    @Autowired
    private MarketSummaryRepository marketSummaryRepository;

    @Autowired
    private TickerRepository tickerRepository;

    private Currency createCurrency() {
        return currencyRepository.save(new Currency("TEST", 0.01, true, "TEST"));
    }

    private Market createMarket() {
        return marketRepository.save(new Market(createCurrency(), createCurrency(), "TEST", 0.01, true));
    }

    private Ticker createTicker() {
        return tickerRepository.save(new Ticker(0.01, 0.01, 0.01));
    }

    private MarketSummary createMarketSummary() {
        Ticker ticker = new Ticker(0.01, 0.01, 0.01);
        return marketSummaryRepository.save(new MarketSummary(createMarket(), 0.01, 0.01, 0.01, 0.01, ticker, new Date(), 2, 2, 0.01));
    }

    private MarketHistory createMarketHistory() {
        return marketHistoryRepository.save(new MarketHistory(createMarket(), FillType.FILL, 2L, 0.01, 0.01, 0.01, OrderType.BUY, new Date()));
    }

    @Test
    public void currencyTest() {
        Assert.assertNotNull(createCurrency());
    }

    @Test
    public void marketTest() {
        Assert.assertNotNull(createMarket());
    }

    @Test
    public void tickerTest() {
        Assert.assertNotNull(createTicker());
    }

    @Test
    public void marketSummaryTest() {
        Assert.assertNotNull(createMarketSummary());
    }

    @Test
    public void marketHistoryTest() {
        Assert.assertNotNull(createMarketHistory());
    }



}
