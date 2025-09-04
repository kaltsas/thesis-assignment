package com.web_apps.student_thesis_management.services_test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;

import com.web_apps.student_thesis_management.dao.ApplicationDAO;
import com.web_apps.student_thesis_management.dao.ProfessorDAO;
import com.web_apps.student_thesis_management.dao.StudentDAO;
import com.web_apps.student_thesis_management.dao.SubjectDAO;
import com.web_apps.student_thesis_management.dao.ThesisDAO;
import com.web_apps.student_thesis_management.model.Application;
import com.web_apps.student_thesis_management.model.ThesisAssignmentRequest;
import com.web_apps.student_thesis_management.model.Professor;
import com.web_apps.student_thesis_management.model.Student;
import com.web_apps.student_thesis_management.model.Subject;
import com.web_apps.student_thesis_management.model.Thesis;
import com.web_apps.student_thesis_management.service.ProfessorService;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class TestProfessorService {
	
	@Autowired
	ProfessorService professorService;
	
	@Autowired
	ProfessorDAO professorRepository;
	
	@Autowired
	SubjectDAO subjectRepository;
	
	@Autowired
	StudentDAO studentRepository;
	
	@Autowired
	ApplicationDAO applicationRepository;
	
	@Autowired
	ThesisDAO thesisRepository;

	@Test
	void testFindById() {
		Professor professor = professorService.findById(1);
		Assertions.assertEquals(1, professor.getProfessorId());
		Assertions.assertEquals("bob", professor.getUsername());
		Assertions.assertEquals("Jhon", professor.getFirstName());
		Assertions.assertEquals("Mendus", professor.getLastName());
		Assertions.assertEquals("robotics", professor.getSpecialty());
	}
	
	@Test
	void testGetLoggedInProfessor() {
		Professor theProfessor = professorService.findById(1);

	    Authentication authentication = mock(Authentication.class);
	    when(authentication.getName()).thenReturn(theProfessor.getUsername());
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    
	    Professor result = professorService.getLoggedInProfessor();
	    Assertions.assertEquals("bob", result.getUsername());
	}
	
	@Test
	void testSaveProfile() {
		Professor professor = new Professor("junathan", "James", "Diaz", "nothing");
		professorService.saveProfile(professor);
		int theId = professor.getProfessorId();
		Professor result = professorRepository.findById(theId);
		Assertions.assertEquals("junathan", result.getUsername());
		Assertions.assertEquals("James", result.getFirstName());
		Assertions.assertEquals("Diaz", result.getLastName());
		Assertions.assertEquals("nothing", result.getSpecialty());
		
		//restore database
		professorRepository.delete(result);
	}
	
	@Test
	void testFindByUsername() {
		Professor professor = professorService.findByUsername("bob");
		Assertions.assertEquals(1, professor.getProfessorId());
		Assertions.assertEquals("bob", professor.getUsername());
		Assertions.assertEquals("Jhon", professor.getFirstName());
		Assertions.assertEquals("Mendus", professor.getLastName());
		Assertions.assertEquals("robotics", professor.getSpecialty());
	}
	
	@Test
	void testSaveSubject() {
		Subject subject = new Subject("one", 1, "no descreption", true, 3);
		professorService.saveSubject(subject);
		int theId = subject.getSubjectId();
		
		Subject testSubject = subjectRepository.findById(theId);
		Assertions.assertNotNull(testSubject);
		Assertions.assertEquals(theId, subject.getSubjectId());
		Assertions.assertEquals("one", subject.getName());
		Assertions.assertEquals(1, subject.getProfessorId());
		Assertions.assertEquals("no descreption", subject.getDescription());
		Assertions.assertEquals(true, subject.getAvailability());
		Assertions.assertEquals(3, subject.getNumberOfSemesters());
		
		//restore database
		subjectRepository.delete(subject);
	}
	
	@Test
	void testListProfessorSubjects() {
		Professor professor = new Professor("junathan", "James", "Diaz", "nothing");
		professorRepository.save(professor);
		int profId = professor.getProfessorId();		
		Subject subject1 = new Subject("one", profId, "no descreption", true, 3);
		Subject subject2 = new Subject("two", profId, "no descreption", true, 3);
		subjectRepository.save(subject1); subjectRepository.save(subject2);
		
		List<Subject> subjects = professorService.listProfessorSubjects(professor);
		Assertions.assertEquals(2, subjects.size());
		Assertions.assertEquals("one", subjects.get(0).getName());
		Assertions.assertEquals("two", subjects.get(1).getName());
		
		//restore database
		subjectRepository.delete(subject1); subjectRepository.delete(subject2);
		professorRepository.delete(professor);
	}
	
	@Test
	void testMatchStudentsWithApplications() {
		Student student1 = new Student("student1", "Jason", "Merulo", 2016, 7.33, 3, false, 1);
		Student student2 = new Student("student2", "Jason", "Merulo", 2016, 7.33, 3, false, 1);
		studentRepository.save(student1); studentRepository.save(student2);
		int studId1=student1.getStudentId(), studId2=student2.getStudentId();
		
		Subject subject1 = new Subject("one", 1, "no descreption", true, 3);
		Subject subject2 = new Subject("two", 1, "no descreption", true, 3);
		subjectRepository.save(subject1); subjectRepository.save(subject2);
		int subjId1=subject1.getSubjectId(), subjId2=subject2.getSubjectId();
		
		Application application1 = new Application(studId1, subjId1, "first message");
		Application application2 = new Application(studId2, subjId2, "second message");
		applicationRepository.save(application1); applicationRepository.save(application2);
		
		List<Application> applications = new ArrayList<Application>();
		applications.add(application1); applications.add(application2);
		List<Student> students = professorService.matchStudentsWithApplications(applications);
		Assertions.assertEquals(2, students.size());
		Assertions.assertEquals("student1", students.get(0).getUsername());
		Assertions.assertEquals("student2", students.get(1).getUsername());
		
		//restore database
		applicationRepository.delete(application1); applicationRepository.delete(application2);
		subjectRepository.delete(subject1); subjectRepository.delete(subject2);
		studentRepository.delete(student1); studentRepository.delete(student2);
	}
	
	@Test
	void testAssignSubject() {
		Student student1 = new Student("student1", "Jason", "Merulo", 2016, 7.33, 1, false, 1);
		Student student2 = new Student("student2", "Petro", "Pascal", 2016, 6.33, 0, false, 1);
		studentRepository.save(student1); studentRepository.save(student2);
		int studId1=student1.getStudentId(), studId2=student2.getStudentId();
		
		Subject subject = new Subject("one", 1, "no descreption", true, 3);
		subjectRepository.save(subject);
		int subjId=subject.getSubjectId();
		
		Application application1 = new Application(studId1, subjId, "first message");
		Application application2 = new Application(studId2, subjId, "second message");
		applicationRepository.save(application1); applicationRepository.save(application2);
		
		List<Application> applications = new ArrayList<Application>();
		applications.add(application1); applications.add(application2);
		ThesisAssignmentRequest request1 = new ThesisAssignmentRequest(applications, 2, -1, -1, subject);
		ThesisAssignmentRequest request2 = new ThesisAssignmentRequest(applications, 3, -1, -1, subject);
		ThesisAssignmentRequest request3 = new ThesisAssignmentRequest(applications, 4, 0, 8.5, subject);	
		int result1 = professorService.assignSubject(request1);
		int result2 = professorService.assignSubject(request2);
		int result3 = professorService.assignSubject(request3);
		
		Assertions.assertEquals(studId1, result1);
		Assertions.assertEquals(studId2, result2);
		Assertions.assertEquals(-1, result3);
		
		//restore database
		applicationRepository.delete(application1); applicationRepository.delete(application2);
		subjectRepository.delete(subject);
		studentRepository.delete(student1); studentRepository.delete(student2);
	}
	
	@Test
	void testListProfessorTheses() {
		Professor professor = new Professor("junathan", "James", "Diaz", "nothing");
		professorRepository.save(professor);
		int profId = professor.getProfessorId();
		Thesis thesis1 = new Thesis("Thesis1", 1, "this is a test thesis entry");
		Thesis thesis2 = new Thesis("Thesis2", profId, "this is a test thesis entry");
		Thesis thesis3 = new Thesis("Thesis3", profId, "this is a test thesis entry");
		thesisRepository.save(thesis1); thesisRepository.save(thesis2); thesisRepository.save(thesis3);
		
		List<Thesis> theses = professorService.listProfessorTheses(professor);
		Assertions.assertEquals(2, theses.size());
		Assertions.assertEquals("Thesis2", theses.get(0).getTitle());
		Assertions.assertEquals("Thesis3", theses.get(1).getTitle());
		
		//restore database
		thesisRepository.delete(thesis1); thesisRepository.delete(thesis2); thesisRepository.delete(thesis3);
		professorRepository.delete(professor);
	}
}
