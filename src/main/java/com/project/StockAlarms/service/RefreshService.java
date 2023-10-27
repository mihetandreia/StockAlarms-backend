package com.project.StockAlarms.service;


import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.parameters.DataType;
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


@Service
public class RefreshService {

    private final Map<StockWrapper, Boolean> stocksToRefresh;

    @Autowired
    private AlarmRepository alarmRepository;

    @Value("${polling.interval}")
    private Duration pollingInterval;

    public RefreshService() {
        stocksToRefresh = new HashMap<>();
        setRefreshEvery15Minutes();
    }

    public void addStockToRefresh(StockWrapper stock) {
        if (!existsInStockToRefresh(stock)) {
            stocksToRefresh.put(stock, false);
        } else {
            System.out.println("Stock-ul " + stock.getStock().getSymbol() + " există deja în stocksToRefresh.");
        }
    }

    public void deleteFromStockToRefresh(String symbol) {
        Iterator<StockWrapper> iterator = stocksToRefresh.keySet().iterator();
        while (iterator.hasNext()) {
            StockWrapper existingStock = iterator.next();
            if (existingStock.getStock().getSymbol().equals(symbol)) {
                iterator.remove();
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
            System.out.println("STOCK " + stock.getStock().getSymbol());
            Boolean value = entry.getValue();
            System.out.println("2.RefreshService last accessed:" + stock.getStock().getSymbol() + stock.getLastAccessed());
            if (stock.getLastAccessed().isBefore(LocalDateTime.now().minus(pollingInterval))) {
                System.out.println("2.1.RefreshService: Setting should refresh " + stock.getStock().getSymbol() + stock.getLastAccessed());
                refreshStockData(stock);
                newStocksToRefresh.put(stock.withLastAccessed(LocalDateTime.now()), true);
            } else {
                newStocksToRefresh.put(stock, value);
            }
        }
        stocksToRefresh.clear();
        stocksToRefresh.putAll(newStocksToRefresh);
    };


    private void refreshStockData(StockWrapper stock) {
        QuoteResponse response = AlphaVantage.api()
                .timeSeries()
                .quote()
                .forSymbol(stock.getStock().getSymbol())
                .dataType(DataType.JSON)
                .fetchSync();
        System.out.println(stock.getStock().getSymbol() + " " +LocalDateTime.now() + "---------------REFRESH--------------------------------");
        stock.setStock(response);
        updateCurrentPriceAndVariance(response.getSymbol(), response.getPrice(), response.getChangePercent());
    }

    public void updateCurrentPriceAndVariance(String symbol, Double currentPrice, Double variance) {
        List<Alarm> alarms = alarmRepository.findAllByStock(symbol);
        System.out.println(alarms);
        for (Alarm alarm : alarms) {
            alarm.setCurrentPrice(currentPrice);
            alarm.setChangePercent(variance);
            alarmRepository.save(alarm);
        }
    }
    }
