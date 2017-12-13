package com.coin.exchanger.market;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Semih Beceren
 */
@Repository
public interface MarketRepository extends CrudRepository<Market, Long> {

    @Query("SELECT m.marketName FROM Market m")
    Set<String> findAllMarketNames();

    Market findByMarketName(String marketName);

    Set<Market> findAll();
}
