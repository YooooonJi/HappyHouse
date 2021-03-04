package com.ssafy.happyhouse.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.UserDto;

@Mapper
public interface UserDao {
	public UserDto login(String userId);
	public int userInsert(UserDto userDto);
	public int userUpdate(UserDto userDto);
	public int userDelete(UserDto userDto);
}

