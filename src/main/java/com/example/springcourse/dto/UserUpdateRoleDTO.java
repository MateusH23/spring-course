package com.example.springcourse.dto;

import com.example.springcourse.domain.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRoleDTO {
	
	private Role role;

}
