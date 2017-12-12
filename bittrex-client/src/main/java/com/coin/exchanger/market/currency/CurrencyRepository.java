package com.coin.exchanger.market.currency;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Semih Beceren
 */
@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {

    @Query("SELECT c.currency FROM Currency c")
    Set<String> findAllCurrencyNames();

    Currency findByCurrency(String currency);
}
