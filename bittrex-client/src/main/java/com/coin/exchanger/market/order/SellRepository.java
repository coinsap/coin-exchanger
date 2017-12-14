package com.coin.exchanger.market.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Semih Beceren
 */
@Repository
public interface SellRepository extends CrudRepository<Sell, Long> {
    Sell findByHash(String hash);
}
