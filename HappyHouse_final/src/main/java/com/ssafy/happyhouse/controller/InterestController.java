package com.ssafy.happyhouse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.InterestDto;
import com.ssafy.happyhouse.model.service.InterestService;
import com.ssafy.happyhouse.model.service.JwtService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/interest")
public class InterestController {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	InterestService interestService;
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final int String = 0;
	
	@PostMapping(value="/register")
	public ResponseEntity<String> interestRegister(@RequestBody InterestDto dto, HttpSession session){
		
		HttpStatus status = null;
		String result = null;
		System.out.println(dto);
		try {
			if(interestService.getInterestCount(dto.getUserId()) >= 10 ) {
				result = "FAIL";
				status = HttpStatus.ACCEPTED;
			}else {

					if(interestService.interestInsert(dto) == SUCCESS) {
						result = "SUCCESS";
						status = HttpStatus.ACCEPTED;
					}else {
						result = "FAIL";
						status = HttpStatus.ACCEPTED;
					}

			}
		} catch (RuntimeException e) {
			logger.error("관심지역 추가 실패 : {}", e);
			result = "FAIL";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, status);
	}
	
	@GetMapping("/list/{userId}")
	public ResponseEntity<Map<String, Object>> getList(@PathVariable String userId, HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		ArrayList<InterestDto> resultList = null;
		HttpStatus status = HttpStatus.ACCEPTED;
//		System.out.println(">>>>>> " + jwtService.get(req.getHeader("auth-token")).get("user"));
		try {
			resultList = interestService.interestList(userId);
			resultMap.put("list", resultList);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			logger.error("정보조회 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		System.out.println(status);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/detail/{interestSeq}")
	public ResponseEntity<Map<String, Object>> getDetail(@PathVariable int interestSeq, HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		InterestDto interestDto = null;
		try {
			interestDto = interestService.interestDetail(interestSeq);
			if(interestDto != null) {
				resultMap.put("result", "SUCCESS");
				resultMap.put("detail", interestDto);
				status = HttpStatus.ACCEPTED;
			}else {
				resultMap.put("result", "FAIL");
				status = HttpStatus.ACCEPTED;
			}
		} catch (RuntimeException e) {
			logger.error("정보조회 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		System.out.println(status);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@DeleteMapping("/delete/{interestSeq}")
	public ResponseEntity<Map<String, Object>> deleteInterest(@PathVariable int interestSeq, HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			if(interestService.interestDelete(interestSeq) == SUCCESS) {
				resultMap.put("result", "SUCCESS");
				status = HttpStatus.ACCEPTED;
			}else {
				resultMap.put("result", "FAIL");
				status = HttpStatus.ACCEPTED;
			}
		} catch (RuntimeException e) {
			logger.error("정보조회 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		System.out.println(status);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	
}
