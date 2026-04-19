package com.portfolioCMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PortfolioCmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioCmsApplication.class, args);
	}

}
