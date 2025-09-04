  package com.web_apps.student_thesis_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web_apps.student_thesis_management.dao.ThesisDAO;
import com.web_apps.student_thesis_management.model.Thesis;

@Service
public class ThesisServiceImpl implements ThesisService{
	
	@Autowired
	private ThesisDAO thesisRepository;
	
	@Override
	@Transactional
	public Thesis findById(int theId) {
		Thesis result = thesisRepository.findById(theId);
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find thesis id - " + theId);
		}
	}
	
	@Override
	@Transactional
	public void calculateFinalGrade(Thesis thesis) {
		double implementationGrade = thesis.getImplementationGrade();
		double reportGrade = thesis.getReportGrade();
		double presentationGrade = thesis.getPresentationGrade();
		double finalGrade = implementationGrade*0.7 + reportGrade*0.15 + presentationGrade*0.15;
		thesis.setFinalGrade(finalGrade);
		thesisRepository.save(thesis);
	}

}
