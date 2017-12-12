package com.coin.exchanger.market.history;

import com.coin.exchanger.market.Market;
import com.coin.exchanger.market.order.OrderType;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Semih Beceren
 */
@Entity
public class MarketHistory {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Market market;

    @Enumerated(EnumType.ORDINAL)
    private FillType fillType;

    private Double quantity;

    private Double price;

    private Double total;

    @Enumerated(EnumType.ORDINAL)
    private OrderType orderType;

    private Date timestamp;

    public MarketHistory(Market market, FillType fillType, Double quantity, Double price, Double total, OrderType orderType, Date timestamp) {
        this.market = market;
        this.fillType = fillType;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.orderType = orderType;
        this.timestamp = timestamp;
    }

    public MarketHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public FillType getFillType() {
        return fillType;
    }

    public void setFillType(FillType fillType) {
        this.fillType = fillType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
