package com.coin.exchanger.remote.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBookHolder {

    private List<OrderHolder> buy;
    private List<OrderHolder> sell;

    public List<OrderHolder> getBuy() {
        return buy;
    }

    public void setBuy(List<OrderHolder> buy) {
        this.buy = buy;
    }

    public List<OrderHolder> getSell() {
        return sell;
    }

    public void setSell(List<OrderHolder> sell) {
        this.sell = sell;
    }
}
