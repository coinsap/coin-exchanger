package com.coin.exchanger.market.history;

import com.coin.exchanger.market.Market;
import com.coin.exchanger.remote.response.OrderType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Semih Beceren
 */
@Repository
public interface MarketHistoryRepository extends CrudRepository<MarketHistory, Long> {
    MarketHistory findByApiId(Long apiId);

    List<MarketHistory> findTop10ByMarketAndOrderTypeOrderByTimestampDesc(Market market, OrderType orderType);
}
