package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.service.JwtService;
import com.ssafy.happyhouse.model.service.UserService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	UserService userService;
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final int String = 0;
	
	@PostMapping(value="/confirm/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto userDto, HttpServletResponse response, HttpSession session){
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			UserDto loginUser = userService.login(userDto.getUserId(), userDto.getUserPassword());
			
			if(loginUser != null) {
//				jwt.io���� Ȯ��
//				�α��� �����ߴٸ� ��ū�� �����Ѵ�.
				String token = jwtService.create(loginUser);
				logger.trace("�α��� ��ū���� : {}", token);
				
//				��ū ������ response�� ����� ������ �������� Map�� ��´�.
//				response.setHeader("auth-token", token);
				resultMap.put("auth-token", token);
				resultMap.put("user-id", loginUser.getUserId());
				resultMap.put("user-name", loginUser.getUserName());
				resultMap.put("user-seq", loginUser.getUserSeq());
//				resultMap.put("status", true);
//				resultMap.put("data", loginUser);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", "���̵� �Ǵ� ��й�ȣ�� �ùٸ��� �ʽ��ϴ�.");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("�α��� ���� : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
		
	}
	
	
	@GetMapping("/info")
	public ResponseEntity<Map<String, Object>> getInfo(HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		System.out.println(">>>>>> " + jwtService.get(req.getHeader("auth-token")).get("user"));
		try {
			// ����ڿ��� ������ �����̴�.
//			String info = memberService.getServerInfo();
			
			resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
//
//			resultMap.put("status", true);
//			resultMap.put("info", info);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			logger.error("������ȸ ���� : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updateInfo(@RequestBody UserDto userDto, HttpServletRequest req, HttpSession session) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		System.out.println(">>>>>> " + jwtService.get(req.getHeader("auth-token")));
		try {
			if(userService.userUpdate(userDto) == SUCCESS) {
				String token = jwtService.create(userDto);
				logger.trace("������ ��ū���� : {}", token);
				resultMap.put("auth-token", token);
				resultMap.put("user-id", userDto.getUserId());
				resultMap.put("user-name", userDto.getUserName());
				status = HttpStatus.ACCEPTED;
			}else {
				resultMap.put("message", "���� ���� ����");
				status = HttpStatus.ACCEPTED;
			}
			
		} catch (RuntimeException e) {
			logger.error("���� ����  ���� : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	

	@PostMapping(value="/confirm/register")
	public ResponseEntity<String> userRegister(@RequestBody UserDto dto, HttpSession session){
		System.out.println("Eldyd????????????????");
		HttpStatus status = null;
		String result = null;
		
		try {
			if( userService.userInsert(dto) == SUCCESS ) {
				result = "SUCCESS";
				status = HttpStatus.ACCEPTED;
			}else {
				result = "FAIL";
				status = HttpStatus.ACCEPTED;
			}
		} catch (RuntimeException e) {
			logger.error("ȸ������  ���� : {}", e);
			result = "FAIL";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, status);
	}
	
	@PostMapping("/leave")
	public ResponseEntity<Map<String, Object>> deleteInfo(@RequestBody UserDto userDto, HttpServletRequest req, HttpSession session) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		System.out.println(">>>>>> " + jwtService.get(req.getHeader("auth-token")));
		try {
			if(userService.userDelete(userDto) == SUCCESS) {

				resultMap.put("result", "SUCCESS");
				status = HttpStatus.ACCEPTED;
			}else {
				resultMap.put("result", "FAIL");
				status = HttpStatus.ACCEPTED;
			}
			
		} catch (RuntimeException e) {
			logger.error("���� ����  ���� : {}", e);
			resultMap.put("result", "FAIL");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
//	@DeleteMapping("/delete")
//	public ResponseEntity<Map<String, Object>> deleteInfo(@RequestBody UserDto userDto, HttpServletRequest req, HttpSession session) {
//		Map<String, Object> resultMap = new HashMap<>();
//		HttpStatus status = null;
////		System.out.println(">>>>>> " + jwtService.get(req.getHeader("auth-token")));
//		try {
//			if(userService.userDelete(userDto) == SUCCESS) {
//
//				resultMap.put("result", "SUCCESS");
//
//				status = HttpStatus.ACCEPTED;
//			}else {
//				resultMap.put("result", "FAIL");
//				status = HttpStatus.ACCEPTED;
//			}
//			
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//			resultMap.put("result", "FAIL");
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		return new ResponseEntity<Map<String, Object>>(resultMap, status);
//	}
//	
//	@GetMapping(value = { "/myInfoModify" })
//	private String myInfoModify() {
//		return ("myInfoModify");
//	}
//	
//	@PostMapping(value="/modify")
//	@ResponseBody
//	public ResponseEntity<UserResultDto> userModify(@RequestBody UserDto dto, HttpSession session){
//		
//		UserResultDto userResultDto = userService.userModify(dto);
//		
//		if( userResultDto.getResult() == SUCCESS ) {
//			session.invalidate();
//			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.OK);
//		}else {
//			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	
//	@GetMapping(value = { "/Withdraw" })
//	private String Withdraw() {
//		return ("Withdraw");
//	}
//	
//	@PostMapping(value="/withdraw")
//	@ResponseBody
//	public ResponseEntity<UserResultDto> userWithdraw(@RequestBody UserDto dto, HttpSession session){
//		
//		UserResultDto userResultDto = userService.userWithdraw(dto);
//		
//		if( userResultDto.getResult() == SUCCESS ) {
//			session.invalidate();
//			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.OK);
//		}else {
//			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	
}
