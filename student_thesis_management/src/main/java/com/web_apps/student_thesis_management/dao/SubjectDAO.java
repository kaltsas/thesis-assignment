package com.web_apps.student_thesis_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web_apps.student_thesis_management.model.Subject;

public interface SubjectDAO extends JpaRepository<Subject, Integer> {

	public Subject findById(int theId);

}
