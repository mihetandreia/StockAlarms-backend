package com.project.StockAlarms.service;

import com.project.StockAlarms.repository.AlarmRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class AlarmServiceTest {
    @Autowired
    private RefreshService refreshService;

    @Autowired
    private StockService stockService;

    @Autowired AlarmService alarmService;

    @Autowired AlarmRepository alarmRepository;

    @Test
    void invoke() throws IOException {

         refreshService.updateAlarmsForSymbol("IBM", 50.0, -6.0);

 /*       List<String> stockSymbols = alarmService.findAllStockFromActiveAlarms();
        System.out.println("SYMBOLS "+stockSymbols);
        List<StockWrapper> result = stockService.findSocks(stockSymbols);
        List<StockWrapper> stocks = refreshService.getStocksToRefresh();
        System.out.println(stocks);
        for (StockWrapper stock: stocks) {
            System.out.println("Stock symbols from DB: " + stock.getStock().getSymbol());
        }
*/
       // alarmService.deleteFromStockToRefresh()

        //System.out.println(alarmService.findAllStockFromActiveAlarms());
    }
}
