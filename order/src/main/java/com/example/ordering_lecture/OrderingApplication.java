package com.example.ordering_lecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrderingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderingApplication.class, args);
	}

}
