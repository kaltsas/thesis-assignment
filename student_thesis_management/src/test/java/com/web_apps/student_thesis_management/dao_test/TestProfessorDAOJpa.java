package com.web_apps.student_thesis_management.dao_test;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.web_apps.student_thesis_management.dao.ProfessorDAO;
import com.web_apps.student_thesis_management.model.Professor;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class TestProfessorDAOJpa {

	@Autowired
	ProfessorDAO professorDAO;
	
	@Test
	void testDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(professorDAO);
	}
	
	@Test
	void testFindByIdReturnsProfessor() {
		Professor storedProfessor = professorDAO.findById(1);
		Assertions.assertNotNull(storedProfessor);
		Assertions.assertEquals(1, storedProfessor.getProfessorId());
		Assertions.assertEquals("bob", storedProfessor.getUsername());
		Assertions.assertEquals("Jhon", storedProfessor.getFirstName());
		Assertions.assertEquals("Mendus", storedProfessor.getLastName());
		Assertions.assertEquals("robotics", storedProfessor.getSpecialty());
	}
	
	@Test
	void testFindByUsernameReturnsProfessor() {
		Optional<Professor> storedProfessors = professorDAO.findByUsername("bob");
		Assertions.assertTrue(storedProfessors.isPresent());
		Professor storedProfessor = storedProfessors.get();
		Assertions.assertNotNull(storedProfessor);
		Assertions.assertEquals(1, storedProfessor.getProfessorId());
		Assertions.assertEquals("bob", storedProfessor.getUsername());
		Assertions.assertEquals("Jhon", storedProfessor.getFirstName());
		Assertions.assertEquals("Mendus", storedProfessor.getLastName());
		Assertions.assertEquals("robotics", storedProfessor.getSpecialty());
	}

}
