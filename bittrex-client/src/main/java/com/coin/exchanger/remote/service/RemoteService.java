package com.coin.exchanger.remote.service;

import com.coin.exchanger.market.order.OrderType;
import com.coin.exchanger.remote.response.*;
import com.coin.exchanger.remote.response.base.ResponseListWrapper;
import com.coin.exchanger.remote.response.base.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RemoteService {

    private final RestTemplate restTemplate;
    private static final String URI = "https://bittrex.com/api/v1.1/public/";



    @Autowired
    public RemoteService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseListWrapper<Market> getMarketsRestCall() {
        return this.restTemplate.getForObject(URI + "getmarkets", (Class<ResponseListWrapper<Market>>) (Class<?>) ResponseListWrapper.class);
    }

    public ResponseListWrapper<Currency> getCurrenciesRestCall(){
        return this.restTemplate.getForObject(URI + "getcurrencies", (Class<ResponseListWrapper<Currency>>) (Class<?>) ResponseListWrapper.class);
    }

    public ResponseWrapper<Ticker> getTickerRestCall(String market) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getticker")
                .queryParam("market", market);
        return this.restTemplate.getForObject(builder.toUriString(), (Class<ResponseWrapper<Ticker>>) (Class<?>) ResponseWrapper.class);
    }

    public ResponseListWrapper<MarketSummary> getMarketSummariesRestCall(){
        return this.restTemplate.getForObject(URI + "getmarketsummaries", (Class<ResponseListWrapper<MarketSummary>>) (Class<?>) ResponseListWrapper.class);
    }
    
    public ResponseWrapper<MarketSummary> getMarketSummaryRestCall(String market){
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getmarketsummary")
                .queryParam("market", market);
        return this.restTemplate.getForObject(builder.toUriString(), (Class<ResponseWrapper<MarketSummary>>) (Class<?>) ResponseWrapper.class);

    }

    public ResponseWrapper<OrderBook> getOrderBookRestCall(String market) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getorderbook")
                .queryParam("market", market)
                .queryParam("type", OrderType.BOTH.name().toLowerCase());

        ResponseWrapper<LinkedHashMap> orders = this.restTemplate.getForObject(builder.toUriString(), (Class<ResponseWrapper<LinkedHashMap>>) (Class<?>) ResponseWrapper.class);
        return getOrderListWrapper(orders);
    }

    private ResponseWrapper<OrderBook> getOrderListWrapper(ResponseWrapper<LinkedHashMap> orders) {
        List<LinkedHashMap<String, Double>> buy = (List<LinkedHashMap<String, Double>>) orders.getResult().get(OrderType.BUY.name().toLowerCase());
        List<LinkedHashMap<String, Double>> sell = (List<LinkedHashMap<String, Double>>) orders.getResult().get(OrderType.SELL.name().toLowerCase());
        ResponseWrapper<OrderBook> responseWrapper = new ResponseWrapper<>();
        OrderBook orderBook = new OrderBook();
        orderBook.setBuy(buy.stream().map(a -> new Order(a.get("Quantity"), a.get("Rate"))).collect(Collectors.toCollection(ArrayList::new)));
        orderBook.setSell(sell.stream().map(a -> new Order(a.get("Quantity"), a.get("Rate"))).collect(Collectors.toCollection(ArrayList::new)));
        responseWrapper.setMessage(orders.getMessage());
        responseWrapper.setSuccess(orders.getSuccess());
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
