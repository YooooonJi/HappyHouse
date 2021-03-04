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
//				jwt.io에서 확인
//				로그인 성공했다면 토큰을 생성한다.
				String token = jwtService.create(loginUser);
				logger.trace("로그인 토큰정보 : {}", token);
				
//				토큰 정보는 response의 헤더로 보내고 나머지는 Map에 담는다.
//				response.setHeader("auth-token", token);
				resultMap.put("auth-token", token);
				resultMap.put("user-id", loginUser.getUserId());
				resultMap.put("user-name", loginUser.getUserName());
				resultMap.put("user-seq", loginUser.getUserSeq());
//				resultMap.put("status", true);
//				resultMap.put("data", loginUser);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", "아이디 또는 비밀번호가 올바르지 않습니다.");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
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
			// 사용자에게 전달할 정보이다.
//			String info = memberService.getServerInfo();
			
			resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
//
//			resultMap.put("status", true);
//			resultMap.put("info", info);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			logger.error("정보조회 실패 : {}", e);
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
				logger.trace("수정된 토큰정보 : {}", token);
				resultMap.put("auth-token", token);
				resultMap.put("user-id", userDto.getUserId());
				resultMap.put("user-name", userDto.getUserName());
				status = HttpStatus.ACCEPTED;
			}else {
				resultMap.put("message", "정보 수정 실패");
				status = HttpStatus.ACCEPTED;
			}
			
		} catch (RuntimeException e) {
			logger.error("정보 수정  실패 : {}", e);
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
			logger.error("회원가입  실패 : {}", e);
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
			logger.error("정보 수정  실패 : {}", e);
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
