package com.web_apps.student_thesis_management.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web_apps.student_thesis_management.model.Student;

public interface StudentDAO extends JpaRepository<Student, Integer> {

	public Student findById(int theId);
	
	public Optional<Student> findByUsername(String username);

}
