package com.ssafy.happyhouse.model.service;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ssafy.happyhouse.model.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {
	
	public static final Logger logger = LoggerFactory.getLogger(JwtService.class);
	
	private String signature = "VUETOKEN";
	private Long expireMin = 60L;

//	�α��� ������ ����� ������ ������� JWTToken�� �����Ͽ� ��ȯ.
	public String create(UserDto userDto) {
		JwtBuilder jwtBuilder = Jwts.builder();
//		JWT Token = Header + Payload + Signature
		
//		Header ����
		jwtBuilder.setHeaderParam("typ", "JWT"); // ��ū�� Ÿ������ ���� ��.

//		Payload ����
		jwtBuilder
			.setSubject("�α�����ū") // ��ū�� ���� ����
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin)) // ��ȿ�Ⱓ ����
			.claim("user", userDto).claim("greeting", "ȯ���մϴ�. " + userDto.getUserName()); // ��� ���� ���� ����.
		
//		signature ����
		jwtBuilder.signWith(SignatureAlgorithm.HS256, signature.getBytes());
		
//		������ ����ȭ ó��
		String jwt = jwtBuilder.compact();
		logger.info("jwt : {}", jwt);
		return jwt;
	}
	
//	���� ���� ��ū�� ����� �����Ȱ��̴� Ȯ�� �ϰ� ������ �ִٸ� RuntimeException�� �߻�.
	public void checkValid(String jwt) {
//		���ܰ� �߻����� ������ OK
		Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(jwt);
	}
	
//	JWT Token�� �м��ؼ� �ʿ��� ������ ��ȯ.
	public Map<String, Object> get(String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(jwt);
        } catch (final Exception e) {
            throw new RuntimeException();
        }

        logger.info("claims : {}", claims);
        // Claims�� Map�� ����ü�̴�.
        return claims.getBody();
    }
}
