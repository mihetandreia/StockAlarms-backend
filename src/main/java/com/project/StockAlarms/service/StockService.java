package com.project.StockAlarms.service;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.project.StockAlarms.model.StockWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StockService {


    private final RefreshService refreshService;

    public StockWrapper findStock(final String symbol){
        QuoteResponse response = AlphaVantage.api()
                .timeSeries()
                .quote()
                .forSymbol(symbol)
                .dataType(DataType.JSON)
                .fetchSync();

        return new StockWrapper(response);
    }

    public List<StockWrapper> findSocks(final List<String> symbols) {
        return symbols.stream().map(this::findStock).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Double findPrice(final StockWrapper stock) throws IOException {
        boolean shouldRefresh = refreshService.shouldRefresh(stock);
        if (shouldRefresh) {
            System.out.println("StockService2: refreshService.shouldRefresh(stock) return truee, " + shouldRefresh);
            return stock.getStock().getPrice();
        }
         return stock.getStock().getPrice();
       //return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getPrice();
    }
/*
    public BigDecimal findLastChangePercent(final StockWrapper stock) throws IOException {
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeInPercent();
    }

    public BigDecimal findChangeFrom200MeanPercent(final StockWrapper stock) throws IOException {
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeFromAvg200InPercent();
    }
*/
}

