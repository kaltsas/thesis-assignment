package com.web_apps.student_thesis_management.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.web_apps.student_thesis_management.model.Student;
import com.web_apps.student_thesis_management.model.Thesis;

@Service
public interface StudentService {
	public Student findById(int theId);

	public void saveProfile(Student theStudent);	
	
	public Student findByUsername(String username);
	
	public Student getLoggedInStudent();
	
	public int applyForSubject(int studentId, int subjectId, String message);
	
	public List<Student> matchStudentsWithTheses(List<Thesis> theses);
	
	public boolean deleteThesis(Student student);
}
