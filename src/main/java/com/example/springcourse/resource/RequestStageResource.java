package com.example.springcourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.service.RequestStageService;

@RestController
@RequestMapping("/stages")
public class RequestStageResource {
	
	@Autowired 
	private RequestStageService stageService;
	
	@PostMapping
	public ResponseEntity<RequestStage> create(@RequestBody RequestStage requestStage) {
		RequestStage createdRequestStage = stageService.createRequestStage(requestStage);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequestStage);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RequestStage> getById(@PathVariable("id") Long id) {
		RequestStage stage = stageService.getRequestStageById(id);
		return ResponseEntity.ok(stage);
	}

}
