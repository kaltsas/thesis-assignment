package com.web_apps.student_thesis_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web_apps.student_thesis_management.model.Application;
import com.web_apps.student_thesis_management.model.Professor;
import com.web_apps.student_thesis_management.model.Subject;

@Service
public interface SubjectService {
	public Subject findById(int theId);
	
	public void deleteById(int theId);
	
	public List<Subject> listAvailableSubjects();
	
	public Professor getSupervisor(int subjectId);
	
	public List<Application> getApplicationsOfSubject(int subjectId);
}
