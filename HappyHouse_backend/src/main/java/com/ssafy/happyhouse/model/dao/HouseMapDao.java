package com.ssafy.happyhouse.model.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseMapDao {
	
	String dongCode(String dongName);
}
