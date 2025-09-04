package com.web_apps.student_thesis_management.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web_apps.student_thesis_management.model.Professor;

@Repository
public interface ProfessorDAO extends JpaRepository<Professor, Integer> {

	public Professor findById(int theId);
	
	public Optional<Professor> findByUsername(String username);
}
