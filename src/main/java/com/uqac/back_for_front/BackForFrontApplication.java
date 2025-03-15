package com.uqac.back_for_front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BackForFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackForFrontApplication.class, args);
	}

}
