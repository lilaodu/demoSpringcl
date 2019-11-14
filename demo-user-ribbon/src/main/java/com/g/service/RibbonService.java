package com.g.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class RibbonService {
	
	private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-DEPT";
	
	
	@Autowired
	RestTemplate restTemplate;
	
	
	public String sayHello() {
		return "";
	}
}
