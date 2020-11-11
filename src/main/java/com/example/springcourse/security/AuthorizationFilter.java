package com.example.springcourse.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springcourse.constants.SecurityConstants;
import com.example.springcourse.resource.exception.ApiError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;

public class AuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (jwt == null || !jwt.startsWith(SecurityConstants.JWT_PROVIDER)) {
			errorProcess(response, "Invalid jwt token");
			return;
		}

		jwt = jwt.replace(SecurityConstants.JWT_PROVIDER, "");

		try {
			Claims claims = new JwtManager().parseToken(jwt);

			String email = claims.getSubject();
			List<String> roles = Arrays.asList(claims.get(SecurityConstants.JWT_ROLE_KEY).toString());

			List<GrantedAuthority> authorities = new ArrayList<>();
			roles.forEach(role -> {
				authorities.add(new SimpleGrantedAuthority(role));
			});

			Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (Exception e) {
			errorProcess(response, e.getMessage());
			return;
		}

		filterChain.doFilter(request, response);

	}

	private void errorProcess(HttpServletResponse response, String msg) throws IOException, JsonProcessingException {
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED.value(), msg, new Date());

		PrintWriter writer = response.getWriter();

		ObjectMapper mapper = new ObjectMapper();
		String apiErrorString = mapper.writeValueAsString(apiError);

		writer.write(apiErrorString);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}

}
