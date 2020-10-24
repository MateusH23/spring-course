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
import com.example.springcourse.domain.User;
import com.example.springcourse.dto.UserLoginDTO;
import com.example.springcourse.service.RequestService;
import com.example.springcourse.service.UserService;

@RestController
@RequestMapping("users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestService requestService;
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user) {
		User createdUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User user) {
		user.setId(id);
		
		User updatedUser = userService.updateUser(user);
		return ResponseEntity.ok(updatedUser);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") Long id) {
		User user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> users = userService.getAllUser();
		return ResponseEntity.ok(users);
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody UserLoginDTO userLoginDTO) {
		User user = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/{id}/requests")
	public ResponseEntity<List<Request>> getAllRequestById(@PathVariable("id") Long id) {
		List<Request> requests = requestService.getAllRequestByOwnerId(id);
		return ResponseEntity.ok(requests);
	}

}
