package com.project.StockAlarms.model;

import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;


import java.time.LocalDateTime;

@Getter
@With
@AllArgsConstructor
public class StockWrapper {

    //private final StockUnit stock;
    private final QuoteResponse stock;
    private final LocalDateTime lastAccessed;

    public StockWrapper(final QuoteResponse stock) {
        this.stock = stock;
        lastAccessed = LocalDateTime.now();
    }

}
