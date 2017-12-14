package com.coin.exchanger.remote.response;


import com.coin.exchanger.market.history.FillType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class MarketHistoryHolder {

    @JsonProperty(value = "Id")
    private Long id;
    @JsonProperty(value = "TimeStamp")
    private Date timeStamp;
    @JsonProperty(value = "Quantity")
    private Double quantity;
    @JsonProperty(value = "Price")
    private Double price;
    @JsonProperty(value = "Total")
    private Double total;
    @JsonProperty(value = "FillType")
    private FillType fillType;
    @JsonProperty(value = "OrderType")
    private OrderType orderType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
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

    public FillType getFillType() {
        return fillType;
    }

    public void setFillType(FillType fillType) {
        this.fillType = fillType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
