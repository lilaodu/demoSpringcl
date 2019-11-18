package com.g.dao;

import org.apache.ibatis.annotations.Mapper;

import com.entity.Users;

@Mapper
public interface UserDao {
	
	public Users getUserInfo(Integer id);
}
