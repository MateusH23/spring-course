package com.example.springcourse.dto;

import javax.validation.constraints.NotNull;

import com.example.springcourse.domain.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRoleDTO {
	
	@NotNull(message = "Role must be not null")
	private Role role;

}
