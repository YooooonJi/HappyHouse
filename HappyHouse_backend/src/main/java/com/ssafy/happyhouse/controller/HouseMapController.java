package com.ssafy.happyhouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.service.HouseMapService;

@RestController
public class HouseMapController {

	@Autowired
	HouseMapService houseMapService;
	
	@GetMapping(value="/dongCode/{dongName}")
	@ResponseBody
	private String dongCode(@PathVariable String dongName){
		System.out.println("asdf");
		String dongCode = null;
		try {
			dongCode = houseMapService.dongCode(dongName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dongCode;
	}
	

}
