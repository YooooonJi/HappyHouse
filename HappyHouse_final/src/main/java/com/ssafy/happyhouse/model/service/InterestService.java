package com.ssafy.happyhouse.model.service;

import java.util.ArrayList;

import com.ssafy.happyhouse.model.InterestDto;

public interface InterestService {
	public int interestInsert(InterestDto interestDto);  
	public ArrayList<InterestDto> interestList(String userId);
	public InterestDto interestDetail(int interestSeq);
	public int interestDelete(int interestSeq);
	public int getInterestCount(String string);
}
