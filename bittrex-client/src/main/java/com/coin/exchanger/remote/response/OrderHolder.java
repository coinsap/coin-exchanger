package com.coin.exchanger.remote.response;


public class OrderHolder {

    public OrderHolder(Double quantity, Double rate) {
        this.quantity = quantity;
        this.rate = rate;
    }

    private Double quantity;
    private Double rate;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
