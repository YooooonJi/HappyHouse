package com.ssafy.happyhouse.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dao.HouseMapDao;

@Service
public class HouseMapServiceImpl implements HouseMapService {

//	private static HouseMapService houseMapService;
//	
//	private HouseMapServiceImpl() {}
//	
//	public static HouseMapService getHouseMapService() {
//		if(houseMapService == null)
//			houseMapService = new HouseMapServiceImpl();
//		return houseMapService;
//	}
	
	@Autowired
	HouseMapDao houseMapDao;


	@Override
	public String dongCode(String dongName) throws Exception {

		return houseMapDao.dongCode(dongName);
	}

}
