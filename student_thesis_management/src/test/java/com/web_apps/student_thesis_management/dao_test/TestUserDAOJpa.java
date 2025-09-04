package com.web_apps.student_thesis_management.dao_test;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.web_apps.student_thesis_management.dao.UserDAO;
import com.web_apps.student_thesis_management.model.User;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class TestUserDAOJpa {
	
	@Autowired
	UserDAO userDAO;
	
	@Test
	void testDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(userDAO);
	}
	
	@Test
	void testFindByIdReturnsProfessor() {
		User storedUser = userDAO.findById(1);
		Assertions.assertNotNull(storedUser);
		Assertions.assertEquals(1, storedUser.getId());
		Assertions.assertEquals("bob", storedUser.getUsername());
		Assertions.assertEquals("123", storedUser.getPassword());
		Assertions.assertEquals("Bob", storedUser.getFirstName());
		Assertions.assertEquals("Marcus", storedUser.getLastName());
		Assertions.assertEquals("Student", storedUser.getRole().getValue());
	}
	
	@Test
	void testFindByUsernameReturnsProfessor() {
	    Optional<User> storedUsers = userDAO.findByUsername("bob");
	    Assertions.assertTrue(storedUsers.isPresent());
	    User storedUser = storedUsers.get();
	    Assertions.assertNotNull(storedUser);
	    Assertions.assertEquals(1, storedUser.getId());
	    Assertions.assertEquals("bob", storedUser.getUsername());
	    Assertions.assertEquals("123", storedUser.getPassword());
	    Assertions.assertEquals("Bob", storedUser.getFirstName());
	    Assertions.assertEquals("Marcus", storedUser.getLastName());
	    Assertions.assertEquals("Student", storedUser.getRole().getValue());
	}
}
