package com.g.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonController {
	
	private static final String REST_URL_PREFIX = "http://providerSayhello/";

	@Autowired
    private RestTemplate restTemplate;
	
	
	@RequestMapping("/say")
	public String sayHello() {
		return restTemplate.getForObject(REST_URL_PREFIX+"home", String.class);
	}
	@RequestMapping("/getUser/{id}")
	public String getUser(@PathVariable("id") int id ) {
		System.out.println(REST_URL_PREFIX+"user/info/"+id);
		System.out.println("1111111"+restTemplate.getForObject(REST_URL_PREFIX+"home", String.class));
		System.out.println("2222222"+restTemplate.getForObject(REST_URL_PREFIX+"home", String.class));
		return restTemplate.getForObject(REST_URL_PREFIX+"user/info/"+id, String.class);
	}
}
