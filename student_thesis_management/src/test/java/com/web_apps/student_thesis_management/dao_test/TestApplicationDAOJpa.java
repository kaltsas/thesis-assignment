package com.web_apps.student_thesis_management.dao_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.web_apps.student_thesis_management.dao.ApplicationDAO;
import com.web_apps.student_thesis_management.model.Application;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class TestApplicationDAOJpa {

	@Autowired
	ApplicationDAO applicationDAO;
	
	@Test
	void testDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(applicationDAO);
	}
	
	@Test
	void testFindByIdReturnsApplication() {
		Application storedApplication = applicationDAO.findById(1);
		Assertions.assertNotNull(storedApplication);
		Assertions.assertEquals(1, storedApplication.getApplicationId());
		Assertions.assertEquals(1, storedApplication.getStudentId());
		Assertions.assertEquals(1, storedApplication.getSubjectId());
		Assertions.assertEquals("I liked that subject", storedApplication.getMessage());
	}

}
