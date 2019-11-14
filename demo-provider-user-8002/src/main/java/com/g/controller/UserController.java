package com.g.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Users;
import com.g.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Value("${server.port}")
	private String port;
	
	@RequestMapping("/user/info/{id}")
	public Map<String,Object> getUserById(@PathVariable("id") int id) {

		Map<String,Object> map =new HashMap<>();
		map.put("port",port);
		map.put("user",userService.getUserInfo(id));
		return  map;
	}
	@RequestMapping("/home")
	public String home() {
		return "hi~ 我是服务提供者，端口：" + port;
	}
	
}
