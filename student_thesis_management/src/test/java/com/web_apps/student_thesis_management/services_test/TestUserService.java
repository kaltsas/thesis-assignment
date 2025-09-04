package com.web_apps.student_thesis_management.services_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.web_apps.student_thesis_management.dao.UserDAO;
import com.web_apps.student_thesis_management.model.Role;
import com.web_apps.student_thesis_management.model.User;
import com.web_apps.student_thesis_management.service.UserService;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class TestUserService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDAO userRepository;

	@Test
	void testSaveUser() {
		User user = new User("@@json", "hard_password", "Jason", "Somerby", Role.PROFESSOR);
		userService.saveUser(user);
		int theId = user.getId();
		User testUser = userRepository.findById(theId);
		Assertions.assertEquals("@@json", testUser.getUsername());
		//password is encrypted in database
		Assertions.assertEquals("Jason", testUser.getFirstName());
		Assertions.assertEquals("Somerby", testUser.getLastName());
		Assertions.assertEquals("Professor", testUser.getRole().getValue());
		
		//restore database
		userRepository.delete(user);
	}
	
	@Test
	void IsUserPresent() {
		boolean result = userService.isUserPresent(userRepository.findById(1));
		Assertions.assertTrue(result);
	}
}
