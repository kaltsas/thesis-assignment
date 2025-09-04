package com.web_apps.student_thesis_management.dao_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.web_apps.student_thesis_management.dao.ThesisDAO;
import com.web_apps.student_thesis_management.model.Thesis;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class TestThesisDAOJpa {

	@Autowired
	ThesisDAO thesisDAO;
	
	@Test
	void testDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(thesisDAO);
	}
	
	@Test
	void testFindByIdReturnsThesis() {
		Thesis storedThesis = thesisDAO.findById(1);
		Assertions.assertNotNull(storedThesis);
		Assertions.assertEquals(1, storedThesis.getThesisId());
		Assertions.assertEquals("web application", storedThesis.getTitle());
		Assertions.assertEquals(1, storedThesis.getProfessorId());
		Assertions.assertEquals("we will use AWS", storedThesis.getDescription());
		Assertions.assertEquals(5.5, storedThesis.getImplementationGrade());
		Assertions.assertEquals(6.3, storedThesis.getReportGrade());
		Assertions.assertEquals(7.9, storedThesis.getPresentationGrade());
		Assertions.assertEquals(8.2, storedThesis.getFinalGrade());
	}
}
