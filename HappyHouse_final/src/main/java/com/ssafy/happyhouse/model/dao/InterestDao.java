package com.ssafy.happyhouse.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.InterestDto;

@Mapper
public interface InterestDao {
	public int interestInsert(InterestDto interestDto);  
	public InterestDto interestDetail(int interestSeq);
	public ArrayList<InterestDto> interestList(String userId);
	public int interestDelete(int interestSeq);
	public int getInterestCount(String userId);
}
