package com.project.StockAlarms;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.project.StockAlarms.model.StockWrapper;
import com.project.StockAlarms.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest

class StockServiceTest {


    @Autowired
    private StockService stockService;


    @Test
    void invoke() throws IOException {
  /*      Config cfg = Config.builder()
                .key("ESC2KL5U5A1X3IT6") // ESC2KL5U5A1X3IT6    WJI6O19AH0RVBBGU
                .timeOut(10)
                .build();

        AlphaVantage.api().init(cfg);

        QuoteResponse response = AlphaVantage.api()
                .timeSeries()
                .quote()
                .forSymbol("AAPL")
                .dataType(DataType.JSON)
                .fetchSync();
        System.out.println(response.toString());
       System.out.println("response "+response.getSymbol() + " " + response.getPrice());
        //StockWrapper stock = new StockWrapper(response);
        //System.out.println(stock.getStock().getSymbol());*/

       final StockWrapper stock = stockService.findStock("IBM");
        System.out.println("TEST: " + stock.getStock().getSymbol() + stock.getStock().getPrice());
        System.out.println("TEST: " + stock);

/*
   StockWrapper stock1 = stockService2.findStock("AAPL");
        System.out.println("TEST: " +stock1.getLastAccessed());
    final Double price = stockService2.findPrice(stock1);
    System.out.println("TEST pret1: " + price);
        try {
            Thread.sleep(15 * 60 * 1000); // Așteaptă 15 minute
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // În cazul în care firul este întrerupt
        }
        final Double price2 = stockService2.findPrice(stock1);
        System.out.println("TEST pret2: " + price2);



        final StockWrapper stock = stockService.findStock("IBM");
        System.out.println("TEST: " + stock.getStock().getLatestTradingDay());
        System.out.println("TEST: " +stock.getStock().getSymbol() + " " + stock.getStock().getPrice() + " " + stock.getStock().getChangePercent());

        List<String> stocks = new ArrayList<>();
        stocks.add("IBM");
        stocks.add("AAPL");
        List<StockWrapper> stocksRes = stockService3.findSocks(stocks);
        stocksRes.forEach((stock3) -> {
            System.out.println("SYMBOL: " + stock3.getStock().getSymbol());
            System.out.println("PRICE: " + stock3.getStock().getPrice());
            System.out.println("CHANGE PERCENT: " + stock3.getStock().getChangePercent());


        });
        System.out.println(stock.getStock().getSymbol());
        StockWrapper stock1 = stockService.findStock("AAPL");
        System.out.println("TEST: " +stock1.getLastAccessed());
        final Double price = stockService.findPrice(stock1);
        System.out.println("TEST pret1: " + price);
        try {
            Thread.sleep(20 * 60 * 1000); // Așteaptă 15 minute
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // În cazul în care firul este întrerupt
        }
        final Double price2 = stockService.findPrice(stock1);
        System.out.println("TEST pret2: " + price2);*/
    }
}

