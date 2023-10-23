package com.project.StockAlarms.service;


import com.project.StockAlarms.model.StockWrapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class RefreshService {

    private final Map<StockWrapper, Boolean> stocksToRefresh;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static final Duration refreshPeriod = Duration.ofMinutes(5);

    public RefreshService() {
        stocksToRefresh = new HashMap<>();
        setRefreshEvery15Minutes();
    }

    public boolean shouldRefresh(final StockWrapper stock) {
        System.out.println("RefreshService: " + stock.getStock() + " " +stock.getLastAccessed());
        System.out.println("RefreshService: CONTAINSkey" + stocksToRefresh.containsKey(stock));
        if (!stocksToRefresh.containsKey(stock)) {
            System.out.println("RefreshService: refresh");
            stocksToRefresh.put(stock, false);
            System.out.println("RefreshService: CONTAINSkey2 " +stocksToRefresh.containsKey(stock));

            return true;
        }
        return stocksToRefresh.get(stock);
    }

    @Scheduled(fixedRate = 50000)
    private void setRefreshEvery15Minutes() {
        System.out.println("RefreshService: setRefresh");
        scheduler.scheduleAtFixedRate(() ->
                stocksToRefresh.forEach((stock, value) -> {
                    System.out.println(stock.getLastAccessed());
                    if (stock.getLastAccessed().isBefore(LocalDateTime.now().minus(refreshPeriod))) {
                        System.out.println("RefreshService: Setting sholud refresh " + stock.getStock() + stock.getLastAccessed()); //.getSymbol());
                        stocksToRefresh.remove(stock);
                        stocksToRefresh.put(stock.withLastAccessed(LocalDateTime.now()), true);
                    }
                }), 0, 5, TimeUnit.MINUTES);
    }
}
