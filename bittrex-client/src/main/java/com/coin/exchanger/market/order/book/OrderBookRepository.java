package com.coin.exchanger.market.order.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Semih Beceren
 */
@Repository
public interface OrderBookRepository extends CrudRepository<OrderBook, Long> {
}
