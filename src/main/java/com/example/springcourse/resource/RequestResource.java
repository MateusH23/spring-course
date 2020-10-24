package com.example.springcourse.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springcourse.domain.Request;
import com.example.springcourse.service.RequestService;

@RestController
@RequestMapping("/requests")
public class RequestResource {
	
	@Autowired
	private RequestService requestService;
	
	@PostMapping
	public ResponseEntity<Request> create(@RequestBody Request request) {
		Request createdRequest = requestService.createRequest(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Request> update(@PathVariable("id") Long id, @RequestBody Request request) {
		request.setId(id);
		Request updatedRequest = requestService.updateRequest(request);
		return ResponseEntity.ok(updatedRequest);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Request> getById(@PathVariable("id") Long id) {
		Request request = requestService.getRequestById(id);
		return ResponseEntity.ok(request);
	}
	
	@GetMapping
	public ResponseEntity<List<Request>> listAll() {
		List<Request> requests = requestService.getAllRequest();
		return ResponseEntity.ok(requests);
	}

}
