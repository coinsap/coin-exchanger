package com.coin.exchanger.remote.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderHolder {
    @JsonProperty(value = "Quantity")
    private Double quantity;
    @JsonProperty(value = "Rate")
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
