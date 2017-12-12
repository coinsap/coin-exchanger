package com.coin.exchanger.market.order;

import com.coin.exchanger.market.order.book.OrderBook;

import javax.persistence.*;
import java.util.List;

/**
 * @author Semih Beceren
 */
@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private Double quantity;
    private Double rate;

    @OneToMany(mappedBy = "order")
    private List<OrderBook> orderBooks;

    public Order() {
    }

    public Order(Double quantity, Double rate) {
        this.quantity = quantity;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public List<OrderBook> getOrderBooks() {
        return orderBooks;
    }

    public void setOrderBooks(List<OrderBook> orderBooks) {
        this.orderBooks = orderBooks;
    }
}
