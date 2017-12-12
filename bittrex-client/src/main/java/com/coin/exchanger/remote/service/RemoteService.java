package com.coin.exchanger.remote.service;

import com.coin.exchanger.order.OrderType;
import com.coin.exchanger.remote.response.*;
import com.coin.exchanger.remote.response.base.ResponseListWrapper;
import com.coin.exchanger.remote.response.base.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class RemoteService {

    private final RestTemplate restTemplate;
    private static final String URI = "https://bittrex.com/api/v1.1/public/";



    @Autowired
    public RemoteService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseListWrapper<Market> getMarketsRestCall() {
        return this.restTemplate.<ResponseListWrapper<Market>>getForObject(URI + "getmarkets", (Class<ResponseListWrapper<Market>>) (Class<?>) ResponseListWrapper.class);
    }

    public ResponseListWrapper<Currency> getCurrenciesRestCall(){
        return this.restTemplate.<ResponseListWrapper<Currency>>getForObject(URI+"getcurrencies",(Class<ResponseListWrapper<Currency>>) (Class<?>)ResponseListWrapper.class);
    }

    public ResponseWrapper<Ticker> getTickerRestCall(String market) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getticker")
                .queryParam("market", market);
        return this.restTemplate.<ResponseWrapper<Ticker>>getForObject(builder.toUriString(), (Class<ResponseWrapper<Ticker>>) (Class<?>) ResponseWrapper.class);
    }

    public ResponseListWrapper<MarketSummary> getMarketSummariesRestCall(){
        return this.restTemplate.<ResponseListWrapper<MarketSummary>>getForObject(URI+"getmarketsummaries",(Class<ResponseListWrapper<MarketSummary>>) (Class<?>)ResponseListWrapper.class);
    }
    
    public ResponseWrapper<MarketSummary> getMarketSummaryRestCall(String market){
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getmarketsummary")
                .queryParam("market", market);
        return  this.restTemplate.<ResponseWrapper<MarketSummary>>getForObject(builder.toUriString(),(Class<ResponseWrapper<MarketSummary>>) (Class<?>)ResponseWrapper.class);

    }

    public ResponseWrapper<OrderBook> getOrderBookRestCall(String market, OrderType orderType){
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getorderbook")
                .queryParam("market", market)
                .queryParam("type", orderType.getKey());

        if(OrderType.BOTH == orderType)
            return this.restTemplate.<ResponseWrapper<OrderBook>>getForObject(builder.toUriString(),(Class<ResponseWrapper<OrderBook>>)(Class<?>)ResponseWrapper.class);
        else
            return getOrderListWrapper(this.restTemplate.getForObject(builder.toUriString(),(Class<ResponseWrapper<List<Order>>>)(Class<?>)ResponseWrapper.class), orderType);

    }

    private ResponseWrapper<OrderBook> getOrderListWrapper(ResponseWrapper<List<Order>> listResponseWrapper, OrderType orderType){
        ResponseWrapper<OrderBook> responseWrapper = new ResponseWrapper<>();
        OrderBook orderBook = new OrderBook();

        if(OrderType.BUY == orderType)
            orderBook.setBuy(listResponseWrapper.getResult());

        if (OrderType.SELL == orderType)
            orderBook.setSell(listResponseWrapper.getResult());

        responseWrapper.setMessage(listResponseWrapper.getMessage());
        responseWrapper.setSuccess(listResponseWrapper.getSuccess());
        responseWrapper.setResult(orderBook);
        return responseWrapper;
    }

    public ResponseListWrapper<MarketHistory> getMarketHistoryRestCall(String market){
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getmarkethistory")
                .queryParam("market", market);
        return this.restTemplate.<ResponseListWrapper<MarketHistory>>getForObject(builder.toUriString(),(Class<ResponseListWrapper<MarketHistory>>)(Class<?>)ResponseListWrapper.class);
    }
}
