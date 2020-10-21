package com.example.springcourse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springcourse.domain.User;
import com.example.springcourse.domain.enums.Role;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@BeforeAll
	@Test
	public void createUserTest() {
		User createdUser = userRepository
				.save(new User(null, "Mateus", "mateus@email.com", "mat3123", Role.ADMINISTRATOR, null, null));

		assertThat(createdUser.getId()).isEqualTo(1L);
	}

	@Test
	public void updateUserTest() {
		User updatedUser = userRepository
				.save(new User(1L, "Mateus Hernani", "mateus@email.com", "mat123", Role.ADMINISTRATOR, null, null));

		assertThat(updatedUser.getName()).isEqualTo("Mateus Hernani");
	}

	@Test
	public void getUserByIdTest() {
		Optional<User> result = userRepository.findById(1L);

		if (result.isPresent()) {
			User user = result.get();

			assertThat(user.getEmail()).isEqualTo("mateus@email.com");
		}
	}

	@Test
	public void getAllUserTest() {
		List<User> users = userRepository.findAll();

		assertThat(users.size()).isEqualTo(1);
	}

	@Test
	public void loginUserTest() {
		Optional<User> result = userRepository.login("mateus@email.com", "mat123");

		if (result.isPresent()) {
			User loggedUser = result.get();

			assertThat(loggedUser.getId()).isEqualTo(1L);
		}
	}

	@Test
	public void updateRoleUserTest() {
		User updatedRoleUser = userRepository
				.save(new User(1L, "Mateus Hernani", "mateus@email.com", "mat123", Role.SIMPLE, null, null));

		assertThat(updatedRoleUser.getRole()).isEqualTo(Role.SIMPLE);
	}

}
