package com.web_apps.student_thesis_management.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.web_apps.student_thesis_management.model.Application;
import com.web_apps.student_thesis_management.model.Professor;
import com.web_apps.student_thesis_management.model.Student;
import com.web_apps.student_thesis_management.model.Subject;
import com.web_apps.student_thesis_management.model.Thesis;
import com.web_apps.student_thesis_management.model.ThesisAssignmentRequest;

@Service
public interface ProfessorService {
	public Professor findById(int theId);

	public void saveProfile(Professor theProfessor);
	
	public Professor findByUsername(String username);
	
	public Professor getLoggedInProfessor();
	
	public void saveSubject(Subject subject);
	
	public List<Subject> listProfessorSubjects(Professor professor);

	public List<Student> matchStudentsWithApplications(List<Application> applications);
	
	public int assignSubject(ThesisAssignmentRequest request);
	
	public List<Thesis> listProfessorTheses(Professor professor);
}
