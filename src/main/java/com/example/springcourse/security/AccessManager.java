package com.example.springcourse.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.User;
import com.example.springcourse.exception.NotFoundException;
import com.example.springcourse.repository.UserRepository;
import com.example.springcourse.service.RequestService;

@Component("accessManager")
public class AccessManager {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RequestService requestService;
	
	public boolean isOwner(Long id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Optional<User> result = userRepository.findByEmail(email);
		
		if(!result.isPresent())
			throw new NotFoundException("There is not user with email = " + email);
		
		User user = result.get();
		
		return user.getId() == id;
		
	}
	
	public boolean isRequestOwner(Long id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Optional<User> result = userRepository.findByEmail(email);
		
		if(!result.isPresent())
			throw new NotFoundException("There is not user with email = " + email);
		
		User user = result.get();
		
		Request request = requestService.getRequestById(id);
		
		return user.getId() == request.getOwner().getId();
	}

}
