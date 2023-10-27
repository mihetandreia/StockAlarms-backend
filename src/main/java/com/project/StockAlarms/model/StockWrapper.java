package com.project.StockAlarms.model;

import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.With;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;

@Getter
@With
@AllArgsConstructor
public class StockWrapper {

    private QuoteResponse stock;
    private LocalDateTime lastAccessed;

    public StockWrapper(final QuoteResponse stock) {
        this.stock = stock;
        lastAccessed = LocalDateTime.now();
    }


    public void setStock(QuoteResponse response) {
        this.stock = response;
    }
}
