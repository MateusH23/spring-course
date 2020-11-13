package com.example.springcourse.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestSaveDTO {
	
	@NotBlank(message = "Subject is required")
	private String subject;
	private String description;
	
	@NotNull(message = "Owner is required")
	private User owner;
	private List<RequestStage> stages = new ArrayList<>();
	
	public Request transformToRequest() {
		return new Request(null, subject, description, null, null, owner, stages, null);
	}

}
