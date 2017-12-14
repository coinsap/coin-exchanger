package com.coin.exchanger.market.summary;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Semih Beceren
 */
@Repository
public interface MarketSummaryRepository extends CrudRepository<MarketSummary, Long> {
    MarketSummary findByHash(String hash);
}
