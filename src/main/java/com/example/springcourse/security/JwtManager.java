package com.example.springcourse.security;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springcourse.constants.SecurityConstants;
import com.example.springcourse.dto.UserLoginResponseDTO;

@Component
public class JwtManager {

	public UserLoginResponseDTO createToken(String email, List<String> roles) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, SecurityConstants.JWT_EXPIRATION_DAYS);

		String token = JWT.create().withSubject(email).withExpiresAt(calendar.getTime())
				.withClaim(SecurityConstants.JWT_ROLE_KEY, roles)
				.sign(Algorithm.HMAC512(SecurityConstants.API_KEY.getBytes()));
		
		Long expireIn = calendar.getTimeInMillis();
		
		return new UserLoginResponseDTO(token, expireIn, SecurityConstants.JWT_PROVIDER);
	}

}
