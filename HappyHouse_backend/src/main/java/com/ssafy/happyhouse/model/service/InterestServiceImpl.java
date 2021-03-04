package com.ssafy.happyhouse.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.InterestDto;
import com.ssafy.happyhouse.model.dao.InterestDao;

@Service
public class InterestServiceImpl implements InterestService {

	@Autowired
	InterestDao interestDao;
	
	@Override
	public int interestInsert(InterestDto interestDto) {
		// TODO Auto-generated method stub
		return interestDao.interestInsert(interestDto);
	}
	

	@Override
	public InterestDto interestDetail(int interestSeq) {
		// TODO Auto-generated method stub
		return interestDao.interestDetail(interestSeq);
	}
	
	@Override
	public ArrayList<InterestDto> interestList(String userId) {
		// TODO Auto-generated method stub
		return interestDao.interestList(userId);
	}

	@Override
	public int interestDelete(int interestSeq) {
		// TODO Auto-generated method stub
		return interestDao.interestDelete(interestSeq);
	}

	@Override
	public int getInterestCount(String userId) {
		// TODO Auto-generated method stub
		return interestDao.getInterestCount(userId);
	}

}
