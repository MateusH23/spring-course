package com.example.springcourse.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springcourse.domain.User;
import com.example.springcourse.exception.NotFoundException;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.repository.UserRepository;
import com.example.springcourse.service.util.HashUtil;

@Service
public class UserService implements UserDetailsService {

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
		Pageable pageable = pr.toSpringPageRequest();
		Page<User> pageUser = userRepository.findAll(pageable);

		PageModel<User> users = new PageModel<>((int) pageUser.getTotalElements(), pageUser.getSize(),
				pageUser.getTotalPages(), pageUser.getContent());
		return users;
	}

	public void updateRole(User user) {
		userRepository.updateRole(user.getId(), user.getRole());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> result = userRepository.findByEmail(username);
		if (!result.isPresent())
			throw new UsernameNotFoundException("Doesn't exist user with email = " + username);

		User user = result.get();
		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(), authorities);

		return userDetails;
	}

}
