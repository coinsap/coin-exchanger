package com.coin.exchanger.market.currency;

import com.coin.exchanger.market.Market;

import javax.persistence.*;
import java.util.List;

/**
 * @author Semih Beceren
 */
@Entity
public class Currency {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String currency;
    private Double txFee;
    private Boolean isActive;
    private String coinType;

    @OneToMany(mappedBy = "baseCurrency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Market> base;

    @OneToMany(mappedBy = "marketCurrency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Market> market;

    public Currency() {
    }

    public Currency(String currency, Double txFee, Boolean isActive, String coinType) {
        this.currency = currency;
        this.txFee = txFee;
        this.isActive = isActive;
        this.coinType = coinType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
}
