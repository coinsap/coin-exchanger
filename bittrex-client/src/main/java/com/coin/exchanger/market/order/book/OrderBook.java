package com.coin.exchanger.market.order.book;

import com.coin.exchanger.market.order.Order;
import com.coin.exchanger.market.order.OrderType;

import javax.persistence.*;

/**
 * @author Semih Beceren
 */
@Entity
public class OrderBook {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private OrderType orderType;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    private Order order;

    public OrderBook() {
    }

    public OrderBook(OrderType orderType, Order order) {
        this.orderType = orderType;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
