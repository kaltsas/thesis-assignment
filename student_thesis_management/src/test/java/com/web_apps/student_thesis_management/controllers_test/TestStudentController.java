package com.web_apps.student_thesis_management.controllers_test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.web_apps.student_thesis_management.controller.StudentController;
import com.web_apps.student_thesis_management.dao.ApplicationDAO;
import com.web_apps.student_thesis_management.dao.StudentDAO;
import com.web_apps.student_thesis_management.dao.ThesisDAO;
import com.web_apps.student_thesis_management.model.Application;
import com.web_apps.student_thesis_management.model.Student;
import com.web_apps.student_thesis_management.model.Thesis;
import com.web_apps.student_thesis_management.service.StudentService;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
class TestStudentController {

	@Autowired
	WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	StudentController studentController;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	StudentDAO studentRepository;
	
	@Autowired
	ApplicationDAO applicationRepository;
	
	@Autowired
	ThesisDAO thesisRepository;
	
	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
		
		//make a logged in student
		Student testStudent = studentService.findById(1);
	    Authentication authentication = mock(Authentication.class);
	    when(authentication.getName()).thenReturn(testStudent.getUsername());
	    SecurityContextHolder.getContext().setAuthentication(authentication);
    }
	
	@Test
	void testStudentControllerIsNotNull() {
		Assertions.assertNotNull(studentController);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	@Test 
	void testGetUSerHomeReturnsPage() throws Exception {
		mockMvc.perform(get("/student/dashboard")).
		andExpect(status().isOk()).
		andExpect(view().name("student/dashboard"));
	}
	
	@Test
	void testShowFormForUpdateReturnsPage() throws Exception {
		mockMvc.perform(get("/student/showFormForUpdate")).
		andExpect(status().isOk()).
		andExpect(view().name("student/student-form"));
	}
	
	@Test
	void testSaveStudent() throws Exception {
		Student student = new Student("json", "Jason", "Merulo", 2016, 7.33, 3, false, 1);
	    
		mockMvc.perform(
				post("/student/save")
				.flashAttr("student", student))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/student/dashboard"));	
		
		//restore database
		studentRepository.delete(student);
	}
	
	@Test
	void testListStudentSubjects() throws Exception {
		mockMvc.perform(get("/student/showSubjects")).
		andExpect(status().isOk()).
		andExpect(view().name("student/subjects"));
	}
	
	@Test
	void testPrepareForm() throws Exception {
		mockMvc.perform(
				post("/student/showFormToApplyForSubject")
				.flashAttr("subjectId", "1"))
				.andExpect(status().isOk())
				.andExpect(view().name("student/new-application-form"));	
	}
	
	@Test
	void testApplyForSubject() throws Exception {
		mockMvc.perform(
				post("/student/applyForSubject")
				.flashAttr("subjectId", "1")
				.flashAttr("message", "!@#$%^&*()(*&^%$#@!@#$%^&*(*&^%$#@!@#$%E^TCVUGIC!%^*FCUVDQW^%!^$@^!"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/student/dashboard"));
		
		//restore database
		this.deleteTempApplication();
	}
	
	@Test
	void testShowThesis() throws Exception {
		mockMvc.perform(get("/student/showThesis")).
		andExpect(status().isOk()).
		andExpect(view().name("student/thesis"));
	}

	@Test
	void testDeleteThesis() throws Exception {
		Thesis thesis1 = new Thesis("Thesis1", 1, "this is a test thesis entry");
		thesis1.setFinalGrade(6.5);
		thesisRepository.save(thesis1);
		int thesisID1 = thesis1.getThesisId();
		Student student1 = studentService.getLoggedInStudent();
		student1.setThesisId(thesisID1);
		studentRepository.save(student1);
		mockMvc.perform(get("/student/deleteThesis")).
		andExpect(status().isOk()).
		andExpect(view().name("student/success"));
		
		
		Thesis thesis2 = new Thesis("Thesis2", 1, "this is a test thesis entry");
		thesis1.setFinalGrade(3.5);
		thesisRepository.save(thesis2);
		int thesisID2 = thesis2.getThesisId();
		Student student2 = studentService.getLoggedInStudent();
		student2.setThesisId(thesisID2);
		studentRepository.save(student2);
		mockMvc.perform(get("/student/deleteThesis")).
		andExpect(status().isOk()).
		andExpect(view().name("student/fail"));
		
		//restore database
		student2.setThesisId(1);
		studentRepository.save(student2);
	}
	
	void deleteTempApplication() {
		List<Application> applications =applicationRepository.findAll();
		int studId = studentService.getLoggedInStudent().getStudentId();
		for(Application application:applications) {
			if(application.getStudentId()==studId && application.getMessage().equals("!@#$%^&*()(*&^%$#@!@#$%^&*(*&^%$#@!@#$%E^TCVUGIC!%^*FCUVDQW^%!^$@^!")) {
				applicationRepository.delete(application);
				break;
			}
		}
	}
}
