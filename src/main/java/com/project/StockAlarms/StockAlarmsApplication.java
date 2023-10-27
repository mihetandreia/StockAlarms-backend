package com.project.StockAlarms;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockAlarmsApplication {


	public static void main(String[] args) {


       	Config cfg = Config.builder()
                .key("ESC2KL5U5A1X3IT6") //WJI6O19AH0RVBBGU
                .timeOut(10)
                .build();

        AlphaVantage.api().init(cfg);

		SpringApplication.run(StockAlarmsApplication.class, args);




		// Welcome to Alpha Vantage!
		// Your API key is: WJI6O19AH0RVBBGU.
		// Please record this API key at a safe place for future data access.
	}


}
