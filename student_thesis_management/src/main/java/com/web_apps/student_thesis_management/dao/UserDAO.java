package com.web_apps.student_thesis_management.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web_apps.student_thesis_management.model.User;

public interface UserDAO extends JpaRepository<User, Integer> {

	public User findById(int theId);
	
	Optional<User> findByUsername(String username);
}
