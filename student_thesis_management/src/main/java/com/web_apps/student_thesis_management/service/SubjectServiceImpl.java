package com.web_apps.student_thesis_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web_apps.student_thesis_management.dao.ApplicationDAO;
import com.web_apps.student_thesis_management.dao.ProfessorDAO;
import com.web_apps.student_thesis_management.dao.SubjectDAO;
import com.web_apps.student_thesis_management.model.Application;
import com.web_apps.student_thesis_management.model.Professor;
import com.web_apps.student_thesis_management.model.Subject;

@Service
public class SubjectServiceImpl implements SubjectService{
	
	@Autowired
	private SubjectDAO subjectRepository;
	
	@Autowired
	private ProfessorDAO professorRepository;
	
	@Autowired
	private ApplicationDAO applicationRepository;
	
	@Override
	@Transactional
	public Subject findById(int theId) {
		Subject result = subjectRepository.findById(theId);
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find subject id - " + theId);
		}
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		subjectRepository.deleteById(theId);
	}
	
	@Override
	@Transactional
	public List<Subject> listAvailableSubjects(){
		List<Subject> validSubjects = new ArrayList<Subject>();
		List<Subject> allSubjects = subjectRepository.findAll();
		for(Subject subject : allSubjects) {
			if(subject.getAvailability()) {
				validSubjects.add(subject);
			}
		}
		return validSubjects;
	}
	
	@Override
	@Transactional
	public Professor getSupervisor(int subjectId) {
		Subject subject = subjectRepository.findById(subjectId);
		return professorRepository.findById(subject.getProfessorId());
	}
	
	@Override
	@Transactional
	public List<Application> getApplicationsOfSubject(int subjectId){
		List<Application> allApplications = applicationRepository.findAll();
		List<Application> validApplications = new ArrayList<Application>();
		for(Application application : allApplications) {
			if(application.getSubjectId()==subjectId) {
				validApplications.add(application);
			}
		}
		return validApplications;
	}
}
