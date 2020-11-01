package com.example.springcourse.dto;

import javax.validation.constraints.NotNull;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.domain.User;
import com.example.springcourse.domain.enums.RequestState;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestStageSaveDTO {
	
	private String description;
	
	@NotNull(message = "State is required")
	private RequestState state;
	
	@NotNull(message = "Owner is required")
	private User owner;
	
	@NotNull(message = "Request is required")
	private Request request;
	
	public RequestStage transformToRequestStage() {
		return new RequestStage(null, null, description, state, owner, request);
	}

}
