package com.coin.exchanger.market.currency;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Semih Beceren
 */
@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}
