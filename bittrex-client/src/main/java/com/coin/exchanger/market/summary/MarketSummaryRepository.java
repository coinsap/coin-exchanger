package com.coin.exchanger.market.summary;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Semih Beceren
 */
@Repository
public interface MarketSummaryRepository extends CrudRepository<MarketSummary, Long> {
    List<MarketSummary> findAll();
    MarketSummary findByHash(String hash);
}
