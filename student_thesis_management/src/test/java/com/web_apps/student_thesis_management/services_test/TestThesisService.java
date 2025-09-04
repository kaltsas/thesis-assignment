package com.web_apps.student_thesis_management.services_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.web_apps.student_thesis_management.service.ThesisService;
import com.web_apps.student_thesis_management.dao.ThesisDAO;
import com.web_apps.student_thesis_management.model.Thesis;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class TestThesisService {

	@Autowired
	ThesisService thesisService;
	
	@Autowired
	ThesisDAO thesisRepository;
	
	@Test
	void testFindById() {
		Thesis thesis = thesisService.findById(1);
		Assertions.assertEquals(1, thesis.getThesisId());
		Assertions.assertEquals("web application", thesis.getTitle());
		Assertions.assertEquals(1, thesis.getProfessorId());
		Assertions.assertEquals("we will use AWS", thesis.getDescription());
		Assertions.assertEquals(5.5, thesis.getImplementationGrade());
		Assertions.assertEquals(6.3, thesis.getReportGrade());
		Assertions.assertEquals(7.9, thesis.getPresentationGrade());
		Assertions.assertEquals(8.2, thesis.getFinalGrade());		
	}
	
	@Test
	void testCalculateFinalGrade() {
		Thesis thesis = thesisService.findById(1);
		thesis.setPresentationGrade(5.0);
		thesis.setImplementationGrade(7.5);
		thesis.setReportGrade(9.5);
		thesisService.calculateFinalGrade(thesis);
		System.out.println(thesis.getFinalGrade());
		Assertions.assertEquals(7.425, thesis.getFinalGrade());
		
		//restore the dummy insertion
		thesis.setImplementationGrade(5.5);
		thesis.setReportGrade(6.3);
		thesis.setPresentationGrade(7.9);
		thesis.setFinalGrade(8.2);
		thesisRepository.save(thesis);
	}

}
