package com.ssafy.happyhouse.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.dao.UserDao;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao userDao;
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	@Override
	public int userInsert(UserDto userDto) {
		int ret = FAIL;
		if( userDao.userInsert(userDto) == 1 ) {
			ret = SUCCESS;
		}else {
			ret = FAIL;
		}
		
		return ret;
	}

	@Override
	public int userDelete(UserDto userDto) {
		int ret = FAIL;
		if( userDao.userDelete(userDto) == 1 ) {
			ret = SUCCESS;
		}else {
			ret = FAIL;
		}
		
		return ret;
	}

	@Override
	public int userUpdate(UserDto userDto) {
		int ret = FAIL;
		if( userDao.userUpdate(userDto) == 1 ) {
			ret = SUCCESS;
		}else {
			ret = FAIL;
		}
		
		return ret;
	}

	@Override
	public UserDto login(String userId, String userPassword) {
		UserDto userDto = userDao.login(userId);
		if( userDto != null && userDto.getUserPassword().equals(userPassword)) {
			return userDto;
		}else {
			return null;
		}
	}

	
}
