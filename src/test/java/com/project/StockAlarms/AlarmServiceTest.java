package com.project.StockAlarms;

import com.project.StockAlarms.model.StockWrapper;
import com.project.StockAlarms.repository.AlarmRepository;
import com.project.StockAlarms.service.AlarmService;
import com.project.StockAlarms.service.RefreshService;
import com.project.StockAlarms.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class AlarmServiceTest {
    @Autowired
    private RefreshService refreshService;

    @Autowired
    private StockService stockService;

    @Autowired
    AlarmService alarmService;

    @Autowired AlarmRepository alarmRepository;

    @Test
    void invoke() throws IOException {

        // refreshService.updateAlarmsForSymbol("SA", 50.0, 6.0);


       /* List<String> stockSymbols = alarmService.findAllStockFromActiveAlarms();
        System.out.println("SYMBOLS "+stockSymbols);
        List<StockWrapper> result = stockService.findSocks(stockSymbols);
        List<StockWrapper> stocks = refreshService.getStocksToRefresh();
        System.out.println(stocks);
        for (StockWrapper stock: stocks) {
            System.out.println("Stock symbols from DB: " + stock.getStock().getSymbol());
        }*/

        /*alarmService.deleteFromStockToRefresh(83L);
        System.out.println(alarmService.findAllStockFromActiveAlarms());*/
    }
}
