package com.example.springcourse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springcourse.domain.User;
import com.example.springcourse.domain.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE email = ?1 AND password = ?2")
	public Optional<User> login(String email, String password);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("UPDATE User SET role = ?2 WHERE id = ?1")
	public int updateRole(Long id, Role role);

}
