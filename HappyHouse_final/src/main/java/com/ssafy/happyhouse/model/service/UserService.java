package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.model.UserDto;

public interface UserService {
	
	public UserDto login(String userId, String userPassword);
	public int userInsert(UserDto userDto);  
	public int userDelete(UserDto userDto);
	public int userUpdate(UserDto userDto);
}
