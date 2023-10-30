package com.project.StockAlarms.impl;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.project.StockAlarms.service.AlphaVantageService;
import org.springframework.stereotype.Service;

@Service
public class AlphaVantageServiceIMPL implements AlphaVantageService {

    @Override
    public QuoteResponse getQuoteResponse(String symbol) {
        QuoteResponse response = AlphaVantage.api()
                .timeSeries()
                .quote()
                .forSymbol(symbol)
                .dataType(DataType.JSON)
                .fetchSync();

        return response;
    }
}
