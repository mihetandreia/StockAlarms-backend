package com.project.StockAlarms.controller;

import com.project.StockAlarms.model.StockWrapper;
import com.project.StockAlarms.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/find/{symbol}")
    @ResponseBody
    public StockWrapper findStock(@PathVariable String symbol) {
        StockWrapper stock = stockService.findStock(symbol);
        return stock;
    }
}
