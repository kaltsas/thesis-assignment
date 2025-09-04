package com.web_apps.student_thesis_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.web_apps.student_thesis_management.dao.StudentDAO;
import com.web_apps.student_thesis_management.dao.ThesisDAO;
import com.web_apps.student_thesis_management.dao.ApplicationDAO;
import com.web_apps.student_thesis_management.model.Application;
import com.web_apps.student_thesis_management.model.Student;
import com.web_apps.student_thesis_management.model.Thesis;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentDAO studentRepository;
	
	@Autowired
	private ApplicationDAO applicationRepository;
	
	@Autowired
	private ThesisDAO thesisRepository;
	
	@Override
	@Transactional
	public Student findById(int theId) {
		Student result = studentRepository.findById(theId);
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find student id - " + theId);
		}
	}
	
	@Override
	@Transactional
	public void saveProfile(Student theStudent) {
		studentRepository.save(theStudent);
	}
	
	@Override
	@Transactional
	public Student findByUsername(String username) {
		Optional<Student> studentOptional = studentRepository.findByUsername(username);
		Student student = studentOptional.orElseThrow(() -> new RuntimeException("Student not found"));
		return student;
	}
	
	@Override
	@Transactional
	public Student getLoggedInStudent() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		Student student = this.findByUsername(username);
		Student theStudent = this.findById(student.getStudentId());
		return theStudent;
	}
	
	@Override
	@Transactional
	public int applyForSubject(int studentId, int subjectId, String message) {
		Application application = new Application(studentId, subjectId, message);
		applicationRepository.save(application);
		return application.getApplicationId();
	}
	
	@Override
	@Transactional
	public List<Student> matchStudentsWithTheses(List<Thesis> theses){
		List<Student> validStudents = new ArrayList<Student>();
		List<Student> allStudents = studentRepository.findAll();
		for(Thesis thesis : theses) {
			for(Student student : allStudents) {
				if(student.getThesisId()==thesis.getThesisId()) {
					validStudents.add(student);
					break;
				}
			}
		}
		return validStudents;
	}
	
	@Override
	@Transactional
	public boolean deleteThesis(Student student) {
		double grade;
		boolean flag = true;
		Thesis theThesis = thesisRepository.findById(student.getThesisId());
		student.setThesisId(-1); //student has no thesis anymore
		grade = theThesis.getFinalGrade();
		if(grade<5.0) {
			flag=false;
		}else {
			student.setGraduate(true);
		}
		thesisRepository.delete(theThesis);
		studentRepository.save(student);
		return flag;
	}
}
