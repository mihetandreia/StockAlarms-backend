package com.project.StockAlarms.service;

import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;

public interface AlphaVantageService {
    QuoteResponse getQuoteResponse(String symbol);
}
