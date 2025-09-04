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

import com.web_apps.student_thesis_management.controller.ProfessorController;
import com.web_apps.student_thesis_management.dao.ApplicationDAO;
import com.web_apps.student_thesis_management.dao.ProfessorDAO;
import com.web_apps.student_thesis_management.dao.StudentDAO;
import com.web_apps.student_thesis_management.dao.SubjectDAO;
import com.web_apps.student_thesis_management.dao.ThesisDAO;
import com.web_apps.student_thesis_management.model.Application;
import com.web_apps.student_thesis_management.model.Professor;
import com.web_apps.student_thesis_management.model.Student;
import com.web_apps.student_thesis_management.model.Subject;
import com.web_apps.student_thesis_management.model.Thesis;
import com.web_apps.student_thesis_management.service.ProfessorService;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
class TestProfessorController {

	@Autowired
	WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
		
	@Autowired
	ProfessorController professorController;
	
	@Autowired
	ProfessorDAO professorRepository;
	
	@Autowired
	ProfessorService professorService;
	
	@Autowired
	SubjectDAO subjectRepository;
	
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
		Professor testProfessor = professorService.findById(1);
	    Authentication authentication = mock(Authentication.class);
	    when(authentication.getName()).thenReturn(testProfessor.getUsername());
	    SecurityContextHolder.getContext().setAuthentication(authentication);
    }
	
	@Test
	void testProfessorControllerIsNotNull() {
		Assertions.assertNotNull(professorController);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	@Test 
	void testGetProfessorHomeReturnsPage() throws Exception {
		mockMvc.perform(get("/professor/dashboard")).
		andExpect(status().isOk()).
		andExpect(view().name("professor/dashboard"));
	}
	
	@Test 
	void testShowFormForUpdateReturnsPage() throws Exception {
		mockMvc.perform(get("/professor/showFormForUpdate")).
		andExpect(status().isOk()).
		andExpect(view().name("professor/professor-form"));
	}
	
	@Test 
	void testSaveProfessorReturnsPage() throws Exception {
		Professor professor = new Professor("junathan", "James", "Diaz", "nothing");
	    
		mockMvc.perform(
				post("/professor/save")
				.flashAttr("professor", professor))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/professor/dashboard"));
		
		//restore database
		professorRepository.deleteById(professor.getProfessorId());
	}
	
	@Test 
	void testShowFormForSubjectReturnsPage() throws Exception {
		mockMvc.perform(get("/professor/showFormToAddSubject")).
		andExpect(status().isOk()).
		andExpect(view().name("professor/new-subject-form"));
	}
	
	@Test 
	void testUpdateSubjectReturnsPage() throws Exception {
		mockMvc.perform(
				post("/professor/showFormToUpdateSubject")
				.param("subjectId", "1"))
				.andExpect(status().isOk())
				.andExpect(view().name("professor/new-subject-form"));
	}
	
	@Test 
	void testListProfessorSubjectsReturnsPage() throws Exception {///LOGIN METHOD
		mockMvc.perform(get("/professor/showSubjects")).
		andExpect(status().isOk()).
		andExpect(view().name("professor/subjects"));
	}
	
	@Test 
	void testAddSubjectReturnsPage() throws Exception {
		Subject subject = new Subject("sub", 1, "no descreption", true, 3);
	    
		mockMvc.perform(
				post("/professor/addSubject")
				.flashAttr("subject", subject))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/professor/showSubjects"));
		
		//restore database
		subjectRepository.deleteById(subject.getSubjectId());
	}
	
	@Test 
	void testDeleteSubjectReturnsPage() throws Exception {
		Subject subject = new Subject("sub", 1, "no descreption", true, 3);
		subjectRepository.save(subject);
	    
		mockMvc.perform(
				post("/professor/deleteSubject")
				.param("subjectId", Integer.toString(subject.getSubjectId())))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/professor/showSubjects"));
	}
	
	@Test 
	void testCheckApplicationsReturnsPage() throws Exception {
		mockMvc.perform(
				post("/professor/checkApplications")
				.param("subjectId", "1"))
				.andExpect(status().isOk())
				.andExpect(view().name("/professor/subject-applications"));
	}
	
	@Test 
	void testAssignSubjectReturnsPage() throws Exception {
		Professor professor = new Professor("junathan", "James", "Diaz", "nothing");
		professorRepository.save(professor);
		Subject subject = new Subject("sub", professor.getProfessorId(), "no descreption", true, 3);
		subjectRepository.save(subject);		
		mockMvc.perform( //scenario = no applications
				post("/professor/assignSubject")
				.param("flag", "1")
				.param("numberOfRemainingCourses", "1")
				.param("grade", "1")
				.param("subjectId", Integer.toString(subject.getSubjectId())))
				.andExpect(status().isOk())
				.andExpect(view().name("professor/show-fail-message"));
		
		Student student = new Student("json", "Jason", "Merulo", 2016, 7.33, 3, false, 1);
		studentRepository.save(student);
		Application application1 = new Application(student.getStudentId(), subject.getSubjectId(), "message");
		applicationRepository.save(application1);
		
		mockMvc.perform( //scenario 2 = no student fits requirments
				post("/professor/assignSubject")
				.param("flag", "4")
				.param("numberOfRemainingCourses", "0")
				.param("grade", "8.5")
				.param("subjectId", Integer.toString(subject.getSubjectId())))
				.andExpect(status().isOk())
				.andExpect(view().name("professor/show-fail-message"));
		
		Application application2 = new Application(student.getStudentId(), subject.getSubjectId(), "message");
		applicationRepository.save(application2);
		
		mockMvc.perform( //scenario 3 = thesis assigned
				post("/professor/assignSubject")
				.param("flag", "2")
				.param("numberOfRemainingCourses", "1")
				.param("grade", "1")
				.param("subjectId", Integer.toString(subject.getSubjectId())))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/professor/showSubjects"));
		
		//restore database
		subjectRepository.deleteById(subject.getSubjectId());
		studentRepository.deleteById(student.getStudentId());
		this.deleteTempThesis(professor.getProfessorId());
		professorRepository.deleteById(professor.getProfessorId());
	}
	
	@Test 
	void testPrepareThesesReturnsPage() throws Exception {///LOGIN METHOD
		mockMvc.perform(get("/professor/showTheses")).
		andExpect(status().isOk()).
		andExpect(view().name("professor/theses"));
	}
	
	@Test 
	void testPrepareThesisReturnsPage() throws Exception {
		mockMvc.perform(
				post("/professor/rateThesis")
				.param("thesisId", "1"))
				.andExpect(status().isOk())
				.andExpect(view().name("professor/thesis-form"));
	}
	
	@Test 
	void testCalculateGradeReturnsPage() throws Exception {
		Thesis thesis = new Thesis("Thesis", 1, "this is a test thesis entry");
		
		mockMvc.perform(
				post("/professor/calculateFinalGrade")
				.flashAttr("thesis", thesis))
				.andExpect(status().isOk())
				.andExpect(view().name("professor/dashboard"));
		
		//restore database
		thesisRepository.deleteById(thesis.getThesisId());
	}
	
	void deleteTempThesis(int profId) {
		List<Thesis> theses =thesisRepository.findAll();
		for(Thesis thesis:theses) {
			if(thesis.getProfessorId()==profId) {
				thesisRepository.delete(thesis);
				break;
			}
		}
	}
}
