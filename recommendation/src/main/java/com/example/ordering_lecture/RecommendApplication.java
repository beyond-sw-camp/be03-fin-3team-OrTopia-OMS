package com.example.ordering_lecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class RecommendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommendApplication.class, args);
	}

}
