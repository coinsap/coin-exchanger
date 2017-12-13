package com.coin.exchanger.remote.service;

import com.coin.exchanger.market.order.OrderType;
import com.coin.exchanger.remote.response.*;
import com.coin.exchanger.remote.response.base.ResponseListWrapper;
import com.coin.exchanger.remote.response.base.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RemoteService {

    private final RestTemplate restTemplate;
    private static final String URI = "https://bittrex.com/api/v1.1/public/";

    @Autowired
    public RemoteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseListWrapper<MarketHolder> getMarketsRestCall() {
        ParameterizedTypeReference<ResponseListWrapper<MarketHolder>> parameterizedTypeReference = new ParameterizedTypeReference<ResponseListWrapper<MarketHolder>>() {};
        return this.restTemplate.exchange(URI + "getmarkets", HttpMethod.GET, null, parameterizedTypeReference).getBody();
    }

    public ResponseListWrapper<CurrencyHolder> getCurrenciesRestCall() {
        ParameterizedTypeReference<ResponseListWrapper<CurrencyHolder>> parameterizedTypeReference = new ParameterizedTypeReference<ResponseListWrapper<CurrencyHolder>>() {};
        return this.restTemplate.exchange(URI + "getcurrencies", HttpMethod.GET, null, parameterizedTypeReference).getBody();
    }

    public ResponseWrapper<TickerHolder> getTickerRestCall(String market) {
        ParameterizedTypeReference<ResponseWrapper<TickerHolder>> parameterizedTypeReference = new ParameterizedTypeReference<ResponseWrapper<TickerHolder>>() {};
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getticker")
                .queryParam("market", market);
        return this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, parameterizedTypeReference).getBody();
    }

    public ResponseListWrapper<MarketSummaryHolder> getMarketSummariesRestCall() {
        ParameterizedTypeReference<ResponseListWrapper<MarketSummaryHolder>> parameterizedTypeReference = new ParameterizedTypeReference<ResponseListWrapper<MarketSummaryHolder>>() {};
        return this.restTemplate.exchange(URI + "getmarketsummaries", HttpMethod.GET, null, parameterizedTypeReference).getBody();
    }

    public ResponseWrapper<MarketSummaryHolder> getMarketSummaryRestCall(String market) {
        ParameterizedTypeReference<ResponseListWrapper<MarketSummaryHolder>> parameterizedTypeReference = new ParameterizedTypeReference<ResponseListWrapper<MarketSummaryHolder>>() {};
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getmarketsummary")
                .queryParam("market", market);
        ResponseListWrapper<MarketSummaryHolder> marketSummaryHolderResponseListWrapper = this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, parameterizedTypeReference).getBody();
        ResponseWrapper<MarketSummaryHolder> marketSummaryHolderResponseWrapper = new ResponseWrapper<>();
        marketSummaryHolderResponseWrapper.setResult(marketSummaryHolderResponseListWrapper.getResult().get(0));
        marketSummaryHolderResponseWrapper.setSuccess(marketSummaryHolderResponseListWrapper.getSuccess());
        marketSummaryHolderResponseWrapper.setMessage(marketSummaryHolderResponseListWrapper.getMessage());
        return marketSummaryHolderResponseWrapper;
    }

    public ResponseWrapper<OrderBookHolder> getOrderBookRestCall(String market) {
        ParameterizedTypeReference<ResponseWrapper<OrderBookHolder>> parameterizedTypeReference = new ParameterizedTypeReference<ResponseWrapper<OrderBookHolder>>() {};
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getorderbook")
                .queryParam("market", market)
                .queryParam("type", OrderType.BOTH.name().toLowerCase());
        return this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, parameterizedTypeReference).getBody();
    }

    public ResponseListWrapper<MarketHistoryHolder> getMarketHistoryRestCall(String market) {
        ParameterizedTypeReference<ResponseListWrapper<MarketHistoryHolder>> parameterizedTypeReference = new ParameterizedTypeReference<ResponseListWrapper<MarketHistoryHolder>>() {};
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(URI+"getmarkethistory")
                .queryParam("market", market);
        return this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, parameterizedTypeReference).getBody();
    }
}
