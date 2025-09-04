package com.web_apps.student_thesis_management.dao_test;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.web_apps.student_thesis_management.dao.StudentDAO;
import com.web_apps.student_thesis_management.model.Student;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class TestStudentDAOJpa {

	@Autowired
	StudentDAO studentDAO;
	
	@Test
	void testDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(studentDAO);
	}
	
	@Test
	void testFindByIdReturnsStudent() {
		Student storedStudent = studentDAO.findById(1);
		Assertions.assertNotNull(storedStudent);
		Assertions.assertEquals(1, storedStudent.getStudentId());
		Assertions.assertEquals("george", storedStudent.getUsername());
		Assertions.assertEquals("George", storedStudent.getFirstName());
		Assertions.assertEquals("Moors", storedStudent.getLastName());
		Assertions.assertEquals(2019, storedStudent.getYear());
		Assertions.assertEquals(8.13, storedStudent.getGrade());
		Assertions.assertEquals(2, storedStudent.getRemainingCourses());
		Assertions.assertEquals(1, storedStudent.getThesisId());
	}
	
	@Test
	void testFindByUsernameReturnsStudent() {
		Optional<Student> storedStudents = studentDAO.findByUsername("george");
		Assertions.assertTrue(storedStudents.isPresent());
		Student storedStudent = storedStudents.get();
		Assertions.assertNotNull(storedStudent);
		Assertions.assertEquals(1, storedStudent.getStudentId());
		Assertions.assertEquals("george", storedStudent.getUsername());
		Assertions.assertEquals("George", storedStudent.getFirstName());
		Assertions.assertEquals("Moors", storedStudent.getLastName());
		Assertions.assertEquals(2019, storedStudent.getYear());
		Assertions.assertEquals(8.13, storedStudent.getGrade());
		Assertions.assertEquals(2, storedStudent.getRemainingCourses());
		Assertions.assertEquals(1, storedStudent.getThesisId());
	}
}
