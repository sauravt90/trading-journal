package com.system.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class TradingJournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingJournalApplication.class, args);
	}

}
