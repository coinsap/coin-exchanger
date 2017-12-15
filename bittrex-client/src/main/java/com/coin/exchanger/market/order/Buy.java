package com.coin.exchanger.market.order;

import com.coin.exchanger.market.Market;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.DigestUtils;

import javax.persistence.*;

/**
 * @author Semih Beceren
 */
@Entity
@Table(indexes = {@Index(name = "buy_hash_Idx", unique = true, columnList = "hash"), @Index(name = "buy_created_date_Idx", columnList = "created_date")})
@EntityListeners(AuditingEntityListener.class)
public class Buy {

    @Id
    @GeneratedValue
    private Long id;

    private Double quantity;
    private Double rate;
    @ManyToOne
    private Market market;
    @Column(unique = true)
    private String hash;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private long createdDate;

    public Buy() {
    }

    public Buy(Double quantity, Double rate, Market market) {
        this.quantity = quantity;
        this.rate = rate;
        this.market = market;
        this.hash = DigestUtils.md5DigestAsHex((market.getMarketName() + quantity.toString() + rate.toString()).getBytes());
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

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
