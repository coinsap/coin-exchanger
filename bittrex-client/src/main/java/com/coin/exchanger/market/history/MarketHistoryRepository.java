package com.coin.exchanger.market.history;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Semih Beceren
 */
@Repository
public interface MarketHistoryRepository extends CrudRepository<MarketHistory, Long> {
    MarketHistory findByApiId(Long apiId);
}
