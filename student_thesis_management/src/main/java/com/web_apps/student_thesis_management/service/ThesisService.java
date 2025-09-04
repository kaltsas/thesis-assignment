package com.web_apps.student_thesis_management.service;

import org.springframework.stereotype.Service;

import com.web_apps.student_thesis_management.model.Thesis;

@Service
public interface ThesisService {
	public Thesis findById(int theId);
	
	public void calculateFinalGrade(Thesis thesis);
}
