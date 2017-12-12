package com.coin.exchanger.market.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Semih Beceren
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
