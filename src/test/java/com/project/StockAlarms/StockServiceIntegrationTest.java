package com.project.StockAlarms;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testFindStockWithValidSymbol() {
        // Define the symbol for the stock you want to test
        String symbol = "AAPL";

        // Perform a request to your endpoint that calls findStock with the symbol
        Response response = RestAssured.get("http://localhost:8080/stocks/find/" + symbol);

        // Assert the response status code (e.g., 200 for success)
        response.then().statusCode(200);

        // Add more assertions to verify the response body as needed
    }

    @Test
    public void testFindStockWithInvalidSymbol() {
        // Define an invalid symbol that doesn't exist
        String symbol = "INVALID";

        // Perform a request to your endpoint that calls findStock with the invalid symbol
        Response response = RestAssured.get("http://localhost:8080/stocks/find/" + symbol);

        // Assert the response status code, which might be different for an invalid symbol
        response.then().statusCode(404); // This is just an example, adjust as needed

        // Add more assertions to verify the response body as needed
    }
}
