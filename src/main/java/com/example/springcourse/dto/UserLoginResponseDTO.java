package com.example.springcourse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginResponseDTO {
	
	private String token;
	private Long expireIn;
	private String provider;

}
