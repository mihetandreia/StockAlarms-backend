package com.project.StockAlarms.service;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.project.StockAlarms.model.StockWrapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class StockService {

    @Autowired
    private RefreshService refreshService;


    public StockWrapper findStock(final String symbol){

        try {
            QuoteResponse response = AlphaVantage.api()
                    .timeSeries()
                    .quote()
                    .forSymbol(symbol)
                    .dataType(DataType.JSON)
                    .fetchSync();
            System.out.println("Response from Alpha Vantage: symbol " + response.getSymbol() + ", price " + response.getPrice());
            StockWrapper stock = new StockWrapper(response);

            if (response.getSymbol() != null) {

                refreshService.addStockToRefresh(stock);
                return stock;
            } else {
                System.err.println("Response from Alpha Vantage is null or incomplete.");
                return null;
            }
        } catch (AlphaVantageException e) {
            System.err.println("Error making call to Alpha Vantage: " + e.getMessage());
            return null;
        }

    }

    public void deleteFromStockToRefresh(String symbol) {
        refreshService.deleteFromStockToRefresh(symbol);
    }

    public List<StockWrapper> findSocks(final List<String> symbols) {
        return symbols.stream().map(this::findStock).filter(Objects::nonNull).collect(Collectors.toList());
    }

}

