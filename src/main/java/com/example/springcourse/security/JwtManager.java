package com.example.springcourse.security;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.springcourse.constants.SecurityConstants;
import com.example.springcourse.dto.UserLoginResponseDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtManager {

	public UserLoginResponseDTO createToken(String email, List<String> roles) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, SecurityConstants.JWT_EXPIRATION_DAYS);

//		String token = JWT.create().withSubject(email).withExpiresAt(calendar.getTime())
//				.withClaim(SecurityConstants.JWT_ROLE_KEY, roles)
//				.sign(Algorithm.HMAC512(SecurityConstants.API_KEY.getBytes()));

		String token = Jwts.builder().setSubject(email).setExpiration(calendar.getTime())
				.claim(SecurityConstants.JWT_ROLE_KEY, roles)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.API_KEY.getBytes()).compact();

		Long expireIn = calendar.getTimeInMillis();

		return new UserLoginResponseDTO(token, expireIn, SecurityConstants.JWT_PROVIDER);
	}

	public Claims parseToken(String jwt) throws JwtException {
		Claims claims = Jwts.parser()
							.setSigningKey(SecurityConstants.API_KEY.getBytes())
							.parseClaimsJws(jwt)
							.getBody();

		return claims;
	}

}
