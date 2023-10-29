package com.project.StockAlarms.service;


import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.project.StockAlarms.model.Alarm;
import com.project.StockAlarms.model.StockWrapper;
import com.project.StockAlarms.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RefreshService {

    private final Map<StockWrapper, Boolean> stocksToRefresh;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private AlphaVantageService alphaVantageService;

    @Autowired
    private MailSenderService mailSenderService;

    @Value("${polling.interval}")
    private Duration pollingInterval;

    public RefreshService() {
        stocksToRefresh = new HashMap<>();
        setRefreshEvery15Minutes();
    }

    public void addStockToRefresh(StockWrapper stock) {
        if (!existsInStockToRefresh(stock)) {
            stocksToRefresh.put(stock, false);
            System.out.println("Stock " + stock.getStock().getSymbol() + " was added in stocksToRefresh.");
        } else {
            System.out.println("Stock " + stock.getStock().getSymbol() + " already exists in stocksToRefresh.");
        }
    }

    public void deleteFromStockToRefresh(String symbol) {
        Iterator<Map.Entry<StockWrapper, Boolean>> iterator = stocksToRefresh.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<StockWrapper, Boolean> entry = iterator.next();
            StockWrapper stock = entry.getKey();
            if (stock.getStock().getSymbol().equals(symbol)) {
                iterator.remove();
                System.out.println("Stock " + symbol + " was deleted from stocksToRefresh.");
            }
        }
    }

    public List<StockWrapper> getStocksToRefresh() {
        List<StockWrapper> stocks = new ArrayList<>();
        for (Map.Entry<StockWrapper, Boolean> entry : stocksToRefresh.entrySet()) {
            StockWrapper stock = entry.getKey();
            stocks.add(stock);
        }
        return stocks;
    }

    public boolean existsInStockToRefresh(StockWrapper stock) {
        String symbol = stock.getStock().getSymbol();
        for (StockWrapper existingStock : stocksToRefresh.keySet()) {
            if (existingStock.getStock().getSymbol().equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    @Scheduled(fixedRateString = "${polling.interval}")
    private void setRefreshEvery15Minutes() {
        System.out.println("STOCKS TO REFRESH " + getStocksToRefresh());
        Map<StockWrapper, Boolean> newStocksToRefresh = new HashMap<>();
        for (Map.Entry<StockWrapper, Boolean> entry : stocksToRefresh.entrySet()) {
            StockWrapper stock = entry.getKey();
            Boolean value = entry.getValue();
            System.out.println("Stock symbol: " + stock.getStock().getSymbol() +", last accessed:" + stock.getLastAccessed());
            System.out.println(stock.getLastAccessed() + " " + LocalDateTime.now() + " "+ LocalDateTime.now().minus(pollingInterval));
            if (stock.getLastAccessed().isBefore(LocalDateTime.now().minus(pollingInterval))) {
                if(refreshStockData(stock)) {
                    newStocksToRefresh.put(stock.withLastAccessed(LocalDateTime.now()), true);
                }
            } else {
                newStocksToRefresh.put(stock, value);
            }
        }
        stocksToRefresh.clear();
        stocksToRefresh.putAll(newStocksToRefresh);
    };


    private boolean refreshStockData(StockWrapper stock) {
        QuoteResponse response = alphaVantageService.getQuoteResponse(stock.getStock().getSymbol());
        System.out.println(stock.getStock().getSymbol() + " " +LocalDateTime.now() + "---------------REFRESH--------------------------------");
        stock.setStock(response);
        return updateAlarmsForSymbol(response.getSymbol(), response.getPrice(), response.getChangePercent());
    }

    public boolean updateAlarmsForSymbol(String symbol, Double currentPrice, Double variance) {
        List<Alarm> alarms = alarmRepository.findAllByStock(symbol)
                .stream()
                .filter(alarm -> alarm.getStatus())
                .collect(Collectors.toList());
        System.out.println(alarms);
        int noOfTriggeredAlarms = 0;
        for (Alarm alarm : alarms) {
            updateCurrentPriceAndVariance(alarm, currentPrice, variance);
            boolean triggeredAlarm = checkAlarmTargets(alarm);
            noOfTriggeredAlarms += triggeredAlarm ? 1 : 0;
        }
        return keepInStocksToRefresh(alarms.size(), noOfTriggeredAlarms, symbol);
    }

    public boolean keepInStocksToRefresh(int alarmsSize, int noOfTriggeredAlarms, String symbol) {
        if (alarmsSize == noOfTriggeredAlarms) {
            deleteFromStockToRefresh(symbol);
            return false;
        }
        return true;
    }

    public void updateCurrentPriceAndVariance(Alarm alarm, Double currentPrice, Double variance) {
        alarm.setCurrentPrice(currentPrice);
        alarm.setChangePercent(variance);
        alarmRepository.save(alarm);
    }

    private boolean checkAlarmTargets(Alarm alarm) {
        if (alarm.getChangePercent() > alarm.getUpperTarget() || alarm.getChangePercent() < alarm.getLowerTarget()) {
            sendMail(alarm);
            return true;
        }
        return false;
    }

    private void sendMail(Alarm alarm) {
        String to = alarm.getUser().getEmail();
        String subject = "Stock Alarm Triggered for " + alarm.getStock();
        String body = "The stock alarm you set for " + alarm.getStock() +" has been triggered.\n\n" +
                "Alarm Details:\n" +
                "- Stock Symbol: "+ alarm.getStock() +"\n" +
                "- Original Price: " + alarm.getPriceWhenAlarmWasDefined() +"\n" +
                "- New Price: " + alarm.getCurrentPrice() +"\n\n" +
                "The stock price has met the conditions you specified, and the alarm is now marked as inactive.\n\n" +
                "Thank you for using our stock alarm service.";
        mailSenderService.sendNewMail(to, subject, body);
        markAlarmAsInactive(alarm);

    }

    private void markAlarmAsInactive(Alarm alarm) {
        alarm.setStatus(false);
        alarmRepository.save(alarm);
    }
}
