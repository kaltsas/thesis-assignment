package com.web_apps.student_thesis_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web_apps.student_thesis_management.model.Thesis;

public interface ThesisDAO extends JpaRepository<Thesis, Integer> {

	public Thesis findById(int theId);

}
