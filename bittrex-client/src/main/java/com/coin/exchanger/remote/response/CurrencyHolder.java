package com.coin.exchanger.remote.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyHolder {
    @JsonProperty
    private String currency;
    @JsonProperty
    private Integer currencyLong;
    @JsonProperty
    private Double txFee;
    @JsonProperty
    private Boolean isActive;
    @JsonProperty
    private String coinType;
    @JsonProperty
    private Object baseAddress;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getCurrencyLong() {
        return currencyLong;
    }

    public void setCurrencyLong(Integer currencyLong) {
        this.currencyLong = currencyLong;
    }

    public Double getTxFee() {
        return txFee;
    }

    public void setTxFee(Double txFee) {
        this.txFee = txFee;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public Object getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(Object baseAddress) {
        this.baseAddress = baseAddress;
    }
}