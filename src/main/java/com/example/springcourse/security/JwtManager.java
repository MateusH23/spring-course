package com.example.springcourse.security;

import java.util.Calendar;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springcourse.constants.SecurityConstants;

public class JwtManager {

	public String createToken(String email, List<String> roles) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, SecurityConstants.JWT_EXPIRATION_DAYS);

		return JWT.create().withSubject(email).withExpiresAt(calendar.getTime())
				.withClaim(SecurityConstants.JWT_ROLE_KEY, roles)
				.sign(Algorithm.HMAC512(SecurityConstants.API_KEY.getBytes()));
	}

}
