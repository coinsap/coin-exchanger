package com.coin.exchanger.bittrexclient;

import com.coin.exchanger.remote.response.*;
import com.coin.exchanger.remote.response.base.ResponseListWrapper;
import com.coin.exchanger.remote.response.base.ResponseWrapper;
import com.coin.exchanger.remote.service.RemoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author Semih Beceren
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RemoteTest {


    @Autowired
    private RemoteService remoteService;

    @Test
    public void testMarkets() {
        ResponseListWrapper<MarketHolder> markets = remoteService.getMarketsRestCall();
        assertNotNull(markets);
        assertEquals(markets.getSuccess(), Boolean.TRUE);
        assertNotNull(markets.getResult());
        assertNotEquals(0, markets.getResult().size());
    }

    @Test
    public void testCurrencies() {
        ResponseListWrapper<CurrencyHolder> currencies = remoteService.getCurrenciesRestCall();
        assertNotNull(currencies);
        assertEquals(currencies.getSuccess(), Boolean.TRUE);
        assertNotNull(currencies.getResult());
        assertNotEquals(0, currencies.getResult().size());
    }

    @Test
    public void testTicker() {
        ResponseWrapper<TickerHolder> ticker = remoteService.getTickerRestCall("BTC-LTC");
        assertNotNull(ticker);
        assertEquals(ticker.getSuccess(), Boolean.TRUE);
        assertNotNull(ticker.getResult());
    }

    @Test
    public void testMarketSummaries() {
        ResponseListWrapper<MarketSummaryHolder> marketSummaries = remoteService.getMarketSummariesRestCall();
        assertNotNull(marketSummaries);
        assertEquals(marketSummaries.getSuccess(), Boolean.TRUE);
        assertNotNull(marketSummaries.getResult());
        assertNotEquals(0, marketSummaries.getResult().size());
    }

    @Test
    public void testMarketSummary() {
        ResponseWrapper<MarketSummaryHolder> marketSummary = remoteService.getMarketSummaryRestCall("BTC-LTC");
        assertNotNull(marketSummary);
        assertEquals(marketSummary.getSuccess(), Boolean.TRUE);
        assertNotNull(marketSummary.getResult());
    }

    @Test
    public void testOrderBook() {
        ResponseWrapper<OrderBookHolder> orderBook = remoteService.getOrderBookRestCall("BTC-LTC");
        assertNotNull(orderBook);
        assertEquals(orderBook.getSuccess(), Boolean.TRUE);
        assertNotNull(orderBook.getResult());
        assertNotNull(orderBook.getResult().getBuy());
        assertNotNull(orderBook.getResult().getSell());
    }

    @Test
    public void testMarketHistory() {
        ResponseListWrapper<MarketHistoryHolder> marketHistories = remoteService.getMarketHistoryRestCall("BTC-LTC");
        assertNotNull(marketHistories);
        assertEquals(marketHistories.getSuccess(), Boolean.TRUE);
        assertNotNull(marketHistories.getResult());
        assertNotEquals(0, marketHistories.getResult().size());
    }

}
