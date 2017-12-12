package com.coin.exchanger.remote.response;

import com.coin.exchanger.order.OrderType;
import com.coin.exchanger.remote.response.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBook {

    private List<Order> buy;
    private List<Order> sell;

    public List<Order> getBuy() {
        return buy;
    }

    public void setBuy(List<Order> buy) {
        this.buy = buy;
    }

    public List<Order> getSell() {
        return sell;
    }

    public void setSell(List<Order> sell) {
        this.sell = sell;
    }
}
