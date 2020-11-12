package com.example.springcourse.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.springcourse.dto.UserLoginResponseDTO;
import com.example.springcourse.dto.UserSaveDTO;
import com.example.springcourse.dto.UserUpdateDTO;
import com.example.springcourse.dto.UserUpdateRoleDTO;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.security.JwtManager;
import com.example.springcourse.service.RequestService;
import com.example.springcourse.service.UserService;

@RestController
@RequestMapping("users")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtManager jwtManager;

	@Secured({ "ROLE_ADMINISTRATOR" })
	@PostMapping
	public ResponseEntity<User> create(@RequestBody @Valid UserSaveDTO userDTO) {
		User user = userDTO.transformToUser();
		User createdUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	
	@PreAuthorize("@accessManager.isOwner(#id)")
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateDTO userDTO) {
		User user = userDTO.transformToUser();
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
	public ResponseEntity<UserLoginResponseDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(),
				userLoginDTO.getPassword());
		Authentication auth = authManager.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(auth);

		org.springframework.security.core.userdetails.User userSpring = 
				(org.springframework.security.core.userdetails.User) auth.getPrincipal();
		
		String email = userSpring.getUsername();
		
		List<String> roles = userSpring.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
		
		UserLoginResponseDTO userLoginResponse = jwtManager.createToken(email, roles);

		return ResponseEntity.ok(userLoginResponse);
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

	@Secured({ "ROLE_ADMINISTRATOR" })
	@PatchMapping("/{id}/role")
	public ResponseEntity<?> updateRole(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateRoleDTO userDTO) {
		User user = new User();
		user.setId(id);
		user.setRole(userDTO.getRole());
		userService.updateRole(user);

		return ResponseEntity.ok().build();
	}

}
