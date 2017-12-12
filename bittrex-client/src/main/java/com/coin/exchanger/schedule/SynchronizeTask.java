package com.coin.exchanger.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Scheduled(fixedRate = 10000)
    public void syncLocalDbAndApiTask() {
        logger.info("Sync DB and API started at: {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }


}
