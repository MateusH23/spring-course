package com.example.springcourse.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
	
	@NotBlank(message = "Name must be not blank")
	private String name;
	
	@Email(message = "Email must be valid")
	private String email;
	
	@NotBlank(message = "Password must be null")
	@Size(min = 7, max = 99, message = "Password must be between 7 and 99 characters")
	private String password;
	
	private List<Request> requests = new ArrayList<>();
	private List<RequestStage> stages = new ArrayList<>();
	
	public User transformToUser() {
		return new User(null, name, email, password, null, requests, stages);
	}

}
