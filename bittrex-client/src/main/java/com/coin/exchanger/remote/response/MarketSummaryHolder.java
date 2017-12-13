package com.coin.exchanger.remote.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class MarketSummaryHolder {
    @JsonProperty(value = "MarketName")
    private  String marketName;
    @JsonProperty(value = "High")
    private Double high;
    @JsonProperty(value = "Low")
    private Double low;
    @JsonProperty(value = "Volume")
    private Double volume;
    @JsonProperty(value = "Last")
    private Double last;
    @JsonProperty(value = "BaseVolume")
    private Double baseVolume;
    @JsonProperty(value = "TimeStamp")
    private Date timeStamp;
    @JsonProperty(value = "Bid")
    private Double bid;
    @JsonProperty(value = "Ask")
    private Double ask;
    @JsonProperty(value = "OpenBuyOrders")
    private Integer openBuyOrders;
    @JsonProperty(value = "OpenSellOrders")
    private Integer openSellOrders;
    @JsonProperty(value = "PrevDay")
    private Double prevDay;
    @JsonProperty(value = "Created")
    private Date created;

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getBaseVolume() {
        return baseVolume;
    }

    public void setBaseVolume(Double baseVolume) {
        this.baseVolume = baseVolume;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

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

    public Integer getOpenBuyOrders() {
        return openBuyOrders;
    }

    public void setOpenBuyOrders(Integer openBuyOrders) {
        this.openBuyOrders = openBuyOrders;
    }

    public Integer getOpenSellOrders() {
        return openSellOrders;
    }

    public void setOpenSellOrders(Integer openSellOrders) {
        this.openSellOrders = openSellOrders;
    }

    public Double getPrevDay() {
        return prevDay;
    }

    public void setPrevDay(Double prevDay) {
        this.prevDay = prevDay;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
