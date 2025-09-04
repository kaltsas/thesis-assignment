package com.web_apps.student_thesis_management.services_test;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.web_apps.student_thesis_management.dao.SubjectDAO;
import com.web_apps.student_thesis_management.model.Application;
import com.web_apps.student_thesis_management.model.Professor;
import com.web_apps.student_thesis_management.model.Subject;
import com.web_apps.student_thesis_management.service.SubjectService;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class TestSubjectService {
	
	@Autowired
	SubjectService subjectService;
	
	@Autowired
	SubjectDAO subjectRepository;

	@Test
	void testFindById() {
		Subject subject = subjectService.findById(1);
		Assertions.assertEquals(1, subject.getSubjectId());
		Assertions.assertEquals("robotics project", subject.getName());
		Assertions.assertEquals(1, subject.getProfessorId());
		Assertions.assertEquals("we will use ROS", subject.getDescription());
		Assertions.assertEquals(false, subject.getAvailability());
		Assertions.assertEquals(2, subject.getNumberOfSemesters());
	}
	
	@Test
	void testDeleteById() {
		Subject subjectBefore = new Subject("sub", 1, "no descreption", true, 3);
		subjectRepository.save(subjectBefore);
		int theId = subjectBefore.getSubjectId();
		
		Subject subjectTest = subjectService.findById(theId);
		Assertions.assertNotNull(subjectTest);
		
		subjectService.deleteById(theId);
		//after deletion there is no entry with id=theId in the database
		Subject subjectAfter= subjectRepository.findById(theId);
		Assertions.assertNull(subjectAfter);
	}
	
	@Test
	void testListAvailableSubjects() {
		Subject subject1 = new Subject("one", 1, "no descreption", true, 3);
		Subject subject2 = new Subject("two", 1, "no descreption", false, 3);
		Subject subject3 = new Subject("three", 1, "no descreption", false, 3);
		Subject subject4 = new Subject("four", 1, "no descreption", true, 3);
		subjectRepository.save(subject1);
		subjectRepository.save(subject2);
		subjectRepository.save(subject3);
		subjectRepository.save(subject4);
		int id1 = subject1.getSubjectId();
		int id2 = subject2.getSubjectId();
		int id3 = subject3.getSubjectId();
		int id4 = subject4.getSubjectId();
		List<Subject> availableSubjects = subjectService.listAvailableSubjects();
		Subject[] testSubjects = new Subject[3];
		testSubjects[2] = null;
		for(Subject subject : availableSubjects) {
			if(subject.getSubjectId()==id1) {
				testSubjects[0] = subject;
			}else if(subject.getSubjectId()==id2) {
				testSubjects[2] = subject;
			}else if(subject.getSubjectId()==id3) {
				testSubjects[2] = subject;
			}else if(subject.getSubjectId()==id4) {
				testSubjects[1] = subject;
			}
		}
		Assertions.assertEquals("one", testSubjects[0].getName());
		Assertions.assertEquals("four", testSubjects[1].getName());
		Assertions.assertNull(testSubjects[2]);
		
		//restore database
		subjectRepository.delete(subject1);
		subjectRepository.delete(subject2);
		subjectRepository.delete(subject3);
		subjectRepository.delete(subject4);
	}
	
	@Test
	void testGetSupervisor() {
		Professor professor = subjectService.getSupervisor(1);
		Assertions.assertEquals(1, professor.getProfessorId());
		Assertions.assertEquals("bob", professor.getUsername());
		Assertions.assertEquals("Jhon", professor.getFirstName());
		Assertions.assertEquals("Mendus", professor.getLastName());
		Assertions.assertEquals("robotics", professor.getSpecialty());
	}
	
	@Test
	void testGetApplicationsOfSubject() {
		List<Application> applications = subjectService.getApplicationsOfSubject(1);
		Assertions.assertEquals(1, applications.size());
		Application application = applications.get(0);
		Assertions.assertEquals(1, application.getApplicationId());
		Assertions.assertEquals(1, application.getStudentId());
		Assertions.assertEquals(1, application.getSubjectId());
		Assertions.assertEquals("I liked that subject", application.getMessage());
	}
}
