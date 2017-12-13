package com.coin.exchanger.remote.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyHolder {
    @JsonProperty(value = "Currency")
    private String currency;
    @JsonProperty(value = "CurrencyLong")
    private String currencyLong;
    @JsonProperty(value = "TxFee")
    private Double txFee;
    @JsonProperty(value = "IsActive")
    private Boolean isActive;
    @JsonProperty(value = "CoinType")
    private String coinType;
    @JsonProperty(value = "BaseAddress")
    private String baseAddress;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyLong() {
        return currencyLong;
    }

    public void setCurrencyLong(String currencyLong) {
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

    public String getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(String baseAddress) {
        this.baseAddress = baseAddress;
    }
}
