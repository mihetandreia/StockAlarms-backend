package com.project.StockAlarms;


import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.project.StockAlarms.service.AlphaVantageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class AlphaVantageServiceIT {

    @Autowired
    private AlphaVantageService alphaVantageService;

    @Test
    public void testGetQuoteResponse() {

        Config cfg = Config.builder()
                .key("WJI6O19AH0RVBBGU")   //  ESC2KL5U5A1X3IT6   WJI6O19AH0RVBBGU    L1XVG31QRLDEB522  7GFJ7UPJBNS4LOSM
                .timeOut(10)
                .build();

        AlphaVantage.api().init(cfg);

        String symbol = "MSFT";

        QuoteResponse response = alphaVantageService.getQuoteResponse(symbol);

        assertNotNull(response);
        assertNotNull(response.getPrice());
    }

    @Test
    public void testGetQuoteResponseWithInvalidSymbol() {

        Config cfg = Config.builder()
                .key("WJI6O19AH0RVBBGU")   //  ESC2KL5U5A1X3IT6   WJI6O19AH0RVBBGU    L1XVG31QRLDEB522  7GFJ7UPJBNS4LOSM
                .timeOut(10)
                .build();

        AlphaVantage.api().init(cfg);

        String invalidSymbol = "INVALID";

        QuoteResponse response = alphaVantageService.getQuoteResponse(invalidSymbol);

        assertNotNull(response);
        if (response.getSymbol() == null) {
            assertNotNull(response.getErrorMessage());
        } else {
            assertNotNull(response.getPrice());
        }
    }

    @Test
    public void testPerformanceUnderLoad() {

        Config cfg = Config.builder()
                .key("WJI6O19AH0RVBBGU")   //  ESC2KL5U5A1X3IT6   WJI6O19AH0RVBBGU    L1XVG31QRLDEB522  7GFJ7UPJBNS4LOSM
                .timeOut(10)
                .build();

        AlphaVantage.api().init(cfg);

        int numberOfRequests = 25;

        for (int i = 0; i < numberOfRequests; i++) {
            QuoteResponse response = alphaVantageService.getQuoteResponse("AAPL");

            assertNotNull(response);
        }
    }
}
