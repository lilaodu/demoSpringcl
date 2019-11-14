package com.g;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class UserProvider8002_App {
	
	public static void main(String[] args) {
		SpringApplication.run(UserProvider8002_App.class, args);
	}
}
