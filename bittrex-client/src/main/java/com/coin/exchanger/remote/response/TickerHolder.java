package com.coin.exchanger.remote.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TickerHolder {
    @JsonProperty(value = "Bid")
    private Double bid;
    @JsonProperty(value = "Ask")
    private Double ask;
    @JsonProperty(value = "Last")
    private Double last;

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }
}
