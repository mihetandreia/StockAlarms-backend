package com.project.StockAlarms.service;

import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.project.StockAlarms.model.StockWrapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class StockService {

    @Autowired
    private RefreshService refreshService;

    @Autowired
    private AlphaVantageService alphaVantageService;


    public StockWrapper findStock(final String symbol){

        try {
            QuoteResponse response = alphaVantageService.getQuoteResponse(symbol);

            System.out.println("Response from Alpha Vantage: symbol " + response.getSymbol() + ", price " + response.getPrice());

            if (response.getSymbol() != null && !response.getSymbol().isEmpty()) {
                StockWrapper stock = new StockWrapper(response);
                refreshService.addStockToRefresh(stock);
                return stock;
            }else if(response.getErrorMessage() != null && !response.getErrorMessage().isEmpty()){
                System.err.println(response.getErrorMessage());
                return null;
            }else {
                System.err.println("Response from Alpha Vantage is null or incomplete.");
                return null;
            }
        } catch (AlphaVantageException e) {
            System.err.println("Error making call to Alpha Vantage: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unknown error: " + e.getMessage());
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

