package com;

import org.springframework.boot.SpringApplication;
import org.apache.commons.logging.Log;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	private static Log logger = null;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("Application Start ! ");
		
		
	}
}
