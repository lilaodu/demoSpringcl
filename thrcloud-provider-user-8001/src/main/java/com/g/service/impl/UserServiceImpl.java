package com.g.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Users;
import com.g.dao.UserDao;
import com.g.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	public Users getUserInfo(Integer id) {
		// TODO Auto-generated method stub
		return userDao.getUserInfo(id);
	}
	
	
	
}
