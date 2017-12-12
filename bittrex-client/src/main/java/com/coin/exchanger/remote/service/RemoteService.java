package com.coin.exchanger.remote.service;

import com.coin.exchanger.market.order.OrderType;
import com.coin.exchanger.remote.response.*;
import com.coin.exchanger.remote.response.base.ResponseListWrapper;
import com.coin.exchanger.remote.response.base.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
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

    public ResponseListWrapper<MarketHolder> getMarketsRestCall() {
        return this.restTemplate.getForObject(URI + "getmarkets", (Class<ResponseListWrapper<MarketHolder>>) (Class<?>) ResponseListWrapper.class);
    }

    public ResponseListWrapper<CurrencyHolder> getCurrenciesRestCall() {
        ParameterizedTypeReference<ResponseListWrapper<CurrencyHolder>> parameterizedTypeReference = new ParameterizedTypeReference<ResponseListWrapper<CurrencyHolder>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };

        return this.restTemplate.exchange(URI + "getcurrencies", HttpMethod.GET, null, parameterizedTypeReference).getBody();
    }

    public ResponseWrapper<TickerHolder> getTickerRestCall(String market) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getticker")
                .queryParam("market", market);
        return this.restTemplate.getForObject(builder.toUriString(), (Class<ResponseWrapper<TickerHolder>>) (Class<?>) ResponseWrapper.class);
    }

    public ResponseListWrapper<MarketSummaryHolder> getMarketSummariesRestCall() {
        return this.restTemplate.getForObject(URI + "getmarketsummaries", (Class<ResponseListWrapper<MarketSummaryHolder>>) (Class<?>) ResponseListWrapper.class);
    }

    public ResponseWrapper<MarketSummaryHolder> getMarketSummaryRestCall(String market) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getmarketsummary")
                .queryParam("market", market);
        return this.restTemplate.getForObject(builder.toUriString(), (Class<ResponseWrapper<MarketSummaryHolder>>) (Class<?>) ResponseWrapper.class);

    }

    public ResponseWrapper<OrderBookHolder> getOrderBookRestCall(String market) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getorderbook")
                .queryParam("market", market)
                .queryParam("type", OrderType.BOTH.name().toLowerCase());

        ResponseWrapper<LinkedHashMap> orders = this.restTemplate.getForObject(builder.toUriString(), (Class<ResponseWrapper<LinkedHashMap>>) (Class<?>) ResponseWrapper.class);
        return getOrderListWrapper(orders);
    }

    private ResponseWrapper<OrderBookHolder> getOrderListWrapper(ResponseWrapper<LinkedHashMap> orders) {
        List<LinkedHashMap<String, Double>> buy = (List<LinkedHashMap<String, Double>>) orders.getResult().get(OrderType.BUY.name().toLowerCase());
        List<LinkedHashMap<String, Double>> sell = (List<LinkedHashMap<String, Double>>) orders.getResult().get(OrderType.SELL.name().toLowerCase());
        ResponseWrapper<OrderBookHolder> responseWrapper = new ResponseWrapper<>();
        OrderBookHolder orderBookHolder = new OrderBookHolder();
        orderBookHolder.setBuy(buy.stream().map(a -> new OrderHolder(a.get("Quantity"), a.get("Rate"))).collect(Collectors.toCollection(ArrayList::new)));
        orderBookHolder.setSell(sell.stream().map(a -> new OrderHolder(a.get("Quantity"), a.get("Rate"))).collect(Collectors.toCollection(ArrayList::new)));
        responseWrapper.setMessage(orders.getMessage());
        responseWrapper.setSuccess(orders.getSuccess());
        responseWrapper.setResult(orderBookHolder);
        return responseWrapper;
    }

    public ResponseListWrapper<MarketHistoryHolder> getMarketHistoryRestCall(String market) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getmarkethistory")
                .queryParam("market", market);
        return this.restTemplate.<ResponseListWrapper<MarketHistoryHolder>>getForObject(builder.toUriString(), (Class<ResponseListWrapper<MarketHistoryHolder>>) (Class<?>) ResponseListWrapper.class);
    }
}
