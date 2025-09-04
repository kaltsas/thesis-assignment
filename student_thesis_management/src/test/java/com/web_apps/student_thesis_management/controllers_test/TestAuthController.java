package com.web_apps.student_thesis_management.controllers_test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.web_apps.student_thesis_management.model.User;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.web_apps.student_thesis_management.controller.AuthController;
import com.web_apps.student_thesis_management.model.Role;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
class TestAuthController {
	
	@Autowired
	WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	AuthController authController;
	
	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
    }
	
	@Test
	void testAuthControllerIsNotNull() {
		Assertions.assertNotNull(authController);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	@Test 
	void testLoginReturnsPage() throws Exception {
		mockMvc.perform(get("/login")).
		andExpect(status().isOk()).
		andExpect(view().name("auth/signin"));
	}
	
	@Test 
	void testRegisterReturnsPage() throws Exception {
		mockMvc.perform(get("/register")).
		andExpect(status().isOk()).
		andExpect(view().name("auth/signup"));
	}
	
	@Test 
	void testSaveUserReturnsPage() throws Exception {
		
	    User user = new User(1, "username", "password", "Jonathan", "Moors", Role.STUDENT);
	    
		mockMvc.perform(
				post("/save")
				.flashAttr("user", user))
				.andExpect(status().isOk())
				.andExpect(view().name("auth/signin"));	
	}

}
