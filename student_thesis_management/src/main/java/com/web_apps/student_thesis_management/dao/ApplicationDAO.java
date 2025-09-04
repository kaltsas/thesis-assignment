package com.web_apps.student_thesis_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web_apps.student_thesis_management.model.Application;

public interface ApplicationDAO  extends JpaRepository<Application, Integer> {

	public Application findById(int theId);

}
