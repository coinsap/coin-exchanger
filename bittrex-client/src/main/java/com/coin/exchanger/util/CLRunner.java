package com.coin.exchanger.util;

import com.coin.exchanger.sync.SynchronizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Semih Beceren
 */
@Component
public class CLRunner implements CommandLineRunner {

    private final SynchronizeService synchronizeService;
    private final ExecutorService executorService;

    @Autowired
    public CLRunner(SynchronizeService synchronizeService) {
        this.synchronizeService = synchronizeService;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void run(String... strings) throws Exception {
        executorService.execute(synchronizeService::syncCurrencies);
        executorService.execute(synchronizeService::syncMarkets);
    }
}
