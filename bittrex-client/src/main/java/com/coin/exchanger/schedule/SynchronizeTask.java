package com.coin.exchanger.schedule;

import com.coin.exchanger.sync.SynchronizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Semih Beceren
 */
@Component
public class SynchronizeTask {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final SynchronizeService synchronizeService;

    @Autowired
    public SynchronizeTask(SynchronizeService synchronizeService) {
        this.synchronizeService = synchronizeService;
    }


    @Scheduled(initialDelay = 50000, fixedRate = 1000)
    public void syncMarketHistory() {
        //logger.info("Start Sync Market History at: {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        synchronizeService.syncMarketHistory();
    }

    @Scheduled(initialDelay = 80000, fixedRate = 3000)
    public void syncGuess(){
        synchronizeService.syncGuess();
    }

//    @Async
//    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void syncMarketSummary() {
        logger.info("Start Sync Market Summary at: {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        synchronizeService.syncMarketSummary();
    }

//    @Async
//    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void syncOrderBook() {
        logger.info("Start Sync Buy Book at: {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        synchronizeService.syncOrderBook();
    }


}
