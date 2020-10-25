package com.example.springcourse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.springcourse.domain.User;
import com.example.springcourse.exception.NotFoundException;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.repository.UserRepository;
import com.example.springcourse.service.util.HashUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {

		String hash = HashUtil.getSecureHash(user.getPassword());
		user.setPassword(hash);

		User createdUser = userRepository.save(user);

		return createdUser;
	}

	public User updateUser(User user) {

		String hash = HashUtil.getSecureHash(user.getPassword());
		user.setPassword(hash);

		User updatedUser = userRepository.save(user);

		return updatedUser;
	}

	public User getUserById(Long id) {
		Optional<User> result = userRepository.findById(id);

		return result.orElseThrow(() -> new NotFoundException("There are no user with id " + id));
	}

	public List<User> getAllUser() {
		List<User> users = userRepository.findAll();

		return users;
	}

	public User login(String email, String password) {

		password = HashUtil.getSecureHash(password);

		Optional<User> loggedUser = userRepository.login(email, password);

		return loggedUser.orElseThrow(() -> new NotFoundException("There are no user with these credentials"));
	}

	public PageModel<User> listAllOnLazyMode(PageRequestModel pr) {
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<User> pageUser = userRepository.findAll(pageable);

		PageModel<User> users = new PageModel<>((int) pageUser.getTotalElements(), pageUser.getSize(),
				pageUser.getTotalPages(), pageUser.getContent());
		return users;
	}
	
	public void updateRole(User user) {
		userRepository.updateRole(user.getId(), user.getRole());
	}

}
