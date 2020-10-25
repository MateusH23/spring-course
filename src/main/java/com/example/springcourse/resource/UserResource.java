package com.example.springcourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.User;
import com.example.springcourse.dto.UserLoginDTO;
import com.example.springcourse.dto.UserUpdateRoleDTO;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
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

//	@GetMapping
//	public ResponseEntity<List<User>> findAll() {
//		List<User> users = userService.getAllUser();
//		return ResponseEntity.ok(users);
//	}

	@GetMapping
	public ResponseEntity<PageModel<User>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		PageRequestModel pr = new PageRequestModel(page, size);
		PageModel<User> users = userService.listAllOnLazyMode(pr);
		return ResponseEntity.ok(users);
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody UserLoginDTO userLoginDTO) {
		User user = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
		return ResponseEntity.ok(user);
	}

//	@GetMapping("/{id}/requests")
//	public ResponseEntity<List<Request>> getAllRequestById(@PathVariable("id") Long id) {
//		List<Request> requests = requestService.getAllRequestByOwnerId(id);
//		return ResponseEntity.ok(requests);
//	}

	@GetMapping("/{id}/requests")
	public ResponseEntity<PageModel<Request>> getAllRequestById(@PathVariable("id") Long id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		PageRequestModel pr = new PageRequestModel(page, size);
		PageModel<Request> requests = requestService.getAllRequestByOwnerIdOnLazyMode(id, pr);

		return ResponseEntity.ok(requests);
	}
	
	@PatchMapping("/{id}/role")
	public ResponseEntity<?> updateRole(@PathVariable("id") Long id, @RequestBody UserUpdateRoleDTO userDTO) {
		User user = new User();
		user.setId(id);
		user.setRole(userDTO.getRole());
		userService.updateRole(user);
		
		return ResponseEntity.ok().build();
	}

}
