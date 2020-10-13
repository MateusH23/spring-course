package com.example.springcourse.domain;

import java.util.Date;

import com.example.springcourse.domain.enums.RequestState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestStage {
	
	private Long id;
	private Date realizationDate;
	private String description;
	private User user;
	private Request request;
	private RequestState state;

}
