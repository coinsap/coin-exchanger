package com.coin.exchanger.remote.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class MarketHolder {

    @JsonProperty(value = "MarketCurrency")
    private String marketCurrency;
    @JsonProperty(value = "BaseCurrency")
    private String baseCurrency;
    @JsonProperty(value = "MarketCurrencyLong")
    private String marketCurrencyLong;
    @JsonProperty(value = "BaseCurrencyLong")
    private String baseCurrencyLong;
    @JsonProperty(value = "MinTradeSize")
    private Double minTradeSize;
    @JsonProperty(value = "MarketName")
    private String marketName;
    @JsonProperty(value = "IsActive")
    private Boolean isActive;
    @JsonProperty(value = "Created")
    private Date created;

    public String getMarketCurrency() {
        return marketCurrency;
    }

    public void setMarketCurrency(String marketCurrency) {
        this.marketCurrency = marketCurrency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getMarketCurrencyLong() {
        return marketCurrencyLong;
    }

    public void setMarketCurrencyLong(String marketCurrencyLong) {
        this.marketCurrencyLong = marketCurrencyLong;
    }

    public String getBaseCurrencyLong() {
        return baseCurrencyLong;
    }

    public void setBaseCurrencyLong(String baseCurrencyLong) {
        this.baseCurrencyLong = baseCurrencyLong;
    }

    public Double getMinTradeSize() {
        return minTradeSize;
    }

    public void setMinTradeSize(Double minTradeSize) {
        this.minTradeSize = minTradeSize;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
