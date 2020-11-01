package com.example.springcourse.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class UserLoginDTO {
	
	@Email(message = "Invalid email address")
	private String email;
	
	@NotBlank(message = "Password is required and must be valid")
	private String password;

}
