package com.coin.exchanger.market;

import com.coin.exchanger.market.currency.Currency;
import com.coin.exchanger.market.history.MarketHistory;
import com.coin.exchanger.market.order.Buy;
import com.coin.exchanger.market.order.Sell;
import com.coin.exchanger.market.summary.MarketSummary;

import javax.persistence.*;
import java.util.List;

/**
 * @author Semih Beceren
 */
@Entity
@Table(indexes = {@Index(name = "market_name_idx", columnList = "marketName", unique = true)})
public class Market {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Currency marketCurrency;
    @ManyToOne
    private Currency baseCurrency;
    @Column(unique = true)
    private String marketName;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "market", fetch = FetchType.LAZY)
    private List<MarketSummary> marketSummaries;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "market", fetch = FetchType.LAZY)
    private List<MarketHistory> marketHistories;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "market", fetch = FetchType.LAZY)
    private List<Buy> buyList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "market", fetch = FetchType.LAZY)
    private List<Sell> selList;
    private Double minTradeSize;
    private Boolean isActive;

    public Market() {
    }

    public Market(Currency marketCurrency, Currency baseCurrency, String marketName, Double minTradeSize, Boolean isActive) {
        this.marketCurrency = marketCurrency;
        this.baseCurrency = baseCurrency;
        this.marketName = marketName;
        this.minTradeSize = minTradeSize;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getMarketCurrency() {
        return marketCurrency;
    }

    public void setMarketCurrency(Currency marketCurrency) {
        this.marketCurrency = marketCurrency;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Double getMinTradeSize() {
        return minTradeSize;
    }

    public void setMinTradeSize(Double minTradeSize) {
        this.minTradeSize = minTradeSize;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public List<MarketSummary> getMarketSummaries() {
        return marketSummaries;
    }

    public void setMarketSummaries(List<MarketSummary> marketSummaries) {
        this.marketSummaries = marketSummaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Market market = (Market) o;

        return id != null ? id.equals(market.id) : market.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public List<MarketHistory> getMarketHistories() {
        return marketHistories;
    }

    public void setMarketHistories(List<MarketHistory> marketHistories) {
        this.marketHistories = marketHistories;
    }

    public List<Buy> getBuyList() {
        return buyList;
    }

    public void setBuyList(List<Buy> buyList) {
        this.buyList = buyList;
    }

    public List<Sell> getSelList() {
        return selList;
    }

    public void setSelList(List<Sell> selList) {
        this.selList = selList;
    }
}
