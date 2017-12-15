package com.coin.exchanger.guess;

import com.coin.exchanger.market.Market;
import com.coin.exchanger.market.history.MarketHistory;
import com.coin.exchanger.market.history.MarketHistoryRepository;
import com.coin.exchanger.remote.response.OrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GuessService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MarketHistoryRepository marketHistoryRepository;

    private static LinkedList<DoubleSummaryStatistics> linkedList = new LinkedList<>();
    private static Double buyPrice = null;

    @Autowired
    public GuessService(MarketHistoryRepository marketHistoryRepository) {
        this.marketHistoryRepository = marketHistoryRepository;
    }

    public void isProper(Market market){

        List<MarketHistory> marketHistories = marketHistoryRepository.findTop10ByMarketAndOrderTypeOrderByTimestampDesc(market, OrderType.BUY);

        DoubleSummaryStatistics doubleSummaryStatistics = marketHistories
                .stream()
                .mapToDouble(MarketHistory::getPrice)
                .summaryStatistics();


//        logger.error("Max: {}", doubleSummaryStatistics.getMax());
//        logger.error("Min: {}", doubleSummaryStatistics.getMin());
//        logger.error("Avg: {}", doubleSummaryStatistics.getAverage());


        if(Objects.isNull(buyPrice) && isFallThreeTimes(3) && isGain(doubleSummaryStatistics, 3)) {
            logger.info("{} adına alım yapıldı. Alinan fiyat: {}", market.getMarketName(), marketHistories.get(0).getPrice());
            buyPrice =  marketHistories.get(0).getPrice();
        }
        else if(Objects.nonNull(buyPrice) && marketHistories.get(0).getPrice() > buyPrice && isFall(doubleSummaryStatistics, 6)){
            logger.info("{} adına alım yapıldı. Satılan fiyat: {}, Kar edilen {}", market.getMarketName(), marketHistories.get(0).getPrice(), marketHistories.get(0).getPrice() - buyPrice);
            buyPrice = null;
        }


        linkedList.addLast(doubleSummaryStatistics);
        if(linkedList.size() > 3) {
            linkedList.removeFirst();
        }



    }

    private boolean isFallThreeTimes(int expectedFallPercentage){
        return linkedList.stream().allMatch(doubleSummaryStatistics -> percentageOfTen(doubleSummaryStatistics) <= (expectedFallPercentage*-1));
    }

    private boolean isGain(DoubleSummaryStatistics doubleSummaryStatistics, int expectedGainPercentage){
        return percentageOfTen(doubleSummaryStatistics) >= expectedGainPercentage;
    }

    private boolean isFall(DoubleSummaryStatistics doubleSummaryStatistics, int expectedGainPercentage){
        return percentageOfTen(doubleSummaryStatistics) <= (expectedGainPercentage*-1);
    }

    private long percentageOfTen(DoubleSummaryStatistics doubleSummaryStatistics){
        double maxDiff = doubleSummaryStatistics.getMax() - doubleSummaryStatistics.getAverage();
        double minDiff = doubleSummaryStatistics.getAverage() - doubleSummaryStatistics.getMin();

        double maxMinDiff = maxDiff - minDiff;

        double percentage = maxMinDiff * 10 / maxDiff;
        return Math.round(percentage);
    }

}
