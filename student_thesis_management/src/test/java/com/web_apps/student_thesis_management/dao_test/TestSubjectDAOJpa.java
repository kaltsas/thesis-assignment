package com.web_apps.student_thesis_management.dao_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.web_apps.student_thesis_management.dao.SubjectDAO;
import com.web_apps.student_thesis_management.model.Subject;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class TestSubjectDAOJpa {

	@Autowired
	SubjectDAO subjectDAO;
	
	@Test
	void testDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(subjectDAO);
	}
	
	@Test
	void testFindByIdReturnsSubject() {
		Subject storedSubject = subjectDAO.findById(1);
		Assertions.assertNotNull(storedSubject);
		Assertions.assertEquals(1, storedSubject.getSubjectId());
		Assertions.assertEquals(1, storedSubject.getProfessorId());
		Assertions.assertEquals("robotics project", storedSubject.getName());
		Assertions.assertEquals("we will use ROS", storedSubject.getDescription());
		Assertions.assertEquals(true, storedSubject.getAvailability());
		Assertions.assertEquals(2, storedSubject.getNumberOfSemesters());
	}

}
