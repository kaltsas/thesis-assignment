package com.web_apps.student_thesis_management.services_test;

import java.util.ArrayList;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;

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
class TestStudentService {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	StudentDAO studentRepository;
	
	@Autowired
	ApplicationDAO applicationRepository;
	
	@Autowired
	ThesisDAO thesisRepository;

	@Test
	void testFindById() {
		Student student = studentService.findById(1);
		Assertions.assertEquals(1, student.getStudentId());
		Assertions.assertEquals("george", student.getUsername());
		Assertions.assertEquals("George", student.getFirstName());
		Assertions.assertEquals("Moors", student.getLastName());
		Assertions.assertEquals(2019, student.getYear());
		Assertions.assertEquals(8.13, student.getGrade());
		Assertions.assertEquals(2, student.getRemainingCourses());
		Assertions.assertTrue(student.isGraduate());
		Assertions.assertEquals(1, student.getThesisId());
	}
	
	@Test
	void testGetLoggedInStudent() {
		Student testStudent = studentService.findById(1);

	    Authentication authentication = mock(Authentication.class);
	    when(authentication.getName()).thenReturn(testStudent.getUsername());
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    
	    Student result = studentService.getLoggedInStudent();
	    Assertions.assertEquals("george", result.getUsername());
	}

	@Test
	void testSaveProfile() {
		Student student = new Student("json", "Jason", "Merulo", 2016, 7.33, 3, false, 1);
		studentService.saveProfile(student);
		int theId = student.getStudentId();
		Student result = studentRepository.findById(theId);
		Assertions.assertEquals("json", result.getUsername());
		Assertions.assertEquals("Jason", result.getFirstName());
		Assertions.assertEquals("Merulo", result.getLastName());
		Assertions.assertEquals(2016, result.getYear());
		Assertions.assertEquals(7.33, result.getGrade());
		Assertions.assertEquals(3, result.getRemainingCourses());
		Assertions.assertFalse(result.isGraduate());
		Assertions.assertEquals(1, result.getThesisId());
		
		//restore database
		studentRepository.delete(student);
	}

	@Test
	void testFindByUsername() {
		Student student =studentService.findByUsername("george");
		Assertions.assertEquals(1, student.getStudentId());
		Assertions.assertEquals("george", student.getUsername());
		Assertions.assertEquals("George", student.getFirstName());
		Assertions.assertEquals("Moors", student.getLastName());
		Assertions.assertEquals(2019, student.getYear());
		Assertions.assertEquals(8.13, student.getGrade());
		Assertions.assertEquals(2, student.getRemainingCourses());
		Assertions.assertTrue(student.isGraduate());
		Assertions.assertEquals(1, student.getThesisId());
	}
	
	@Test
	void testApplyForSubject() {
		int applicationId = studentService.applyForSubject(1, 1, "testing application");
		Application result = applicationRepository.findById(applicationId);
		Assertions.assertEquals(applicationId, result.getApplicationId());
		Assertions.assertEquals(1, result.getStudentId());
		Assertions.assertEquals(1, result.getSubjectId());
		Assertions.assertEquals("testing application", result.getMessage());
		
		//restore database
		applicationRepository.delete(result);
	}
	
	@Test
	void testMatchStudentsWithTheses() {
		//prepare lists for the test
		Thesis thesis1 = new Thesis("Thesis1", 1, "this is a test thesis entry");
		Thesis thesis2 = new Thesis("Thesis2", 1, "this is a test thesis entry");
		Thesis thesis3 = new Thesis("Thesis3", 1, "this is a test thesis entry");
		thesisRepository.save(thesis1); thesisRepository.save(thesis2); thesisRepository.save(thesis3);
		int thesisId1=thesis1.getThesisId(), thesisId2=thesis2.getThesisId(), thesisId3=thesis3.getThesisId();
		Student student1 = new Student("first", "Jason", "Merulo", 2016, 7.33, 3, false, thesisId1);
		Student student2 = new Student("second", "Jason", "Merulo", 2016, 7.33, 3, false, thesisId2);
		Student student3 = new Student("third", "Jason", "Merulo", 2016, 7.33, 3, false, thesisId3);
		studentRepository.save(student1); studentRepository.save(student2); studentRepository.save(student3);
		List<Thesis> theses = new ArrayList<Thesis>();
		theses.add(thesis1); theses.add(thesis2); theses.add(thesis3);
		List<Student> students = studentService.matchStudentsWithTheses(theses);
		
		//test
		Assertions.assertEquals("first", students.get(0).getUsername());
		Assertions.assertEquals("second", students.get(1).getUsername());
		Assertions.assertEquals("third", students.get(2).getUsername());
		
		//restore database
		studentRepository.delete(student1); studentRepository.delete(student2); studentRepository.delete(student3);
		thesisRepository.delete(thesis1); thesisRepository.delete(thesis2); thesisRepository.delete(thesis3);
	}
	
	@Test
	void testDeleteThesis() {
		Thesis thesis = new Thesis("Thesis", 1, "this is a test thesis entry");
		thesisRepository.save(thesis);
		int thesisId = thesis.getThesisId();
		Thesis resultBefore = thesisRepository.findById(thesisId);
		Assertions.assertNotNull(resultBefore);
		
		Student student = new Student("student_deleter", "Jason", "Merulo", 2016, 7.33, 3, false, thesisId);
		studentService.deleteThesis(student);
		Thesis resultAfter = thesisRepository.findById(thesisId);
		Assertions.assertNull(resultAfter);
		
		//restore database
		studentRepository.delete(student);
	}
}
