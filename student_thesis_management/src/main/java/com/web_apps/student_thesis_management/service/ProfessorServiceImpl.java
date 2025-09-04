package com.web_apps.student_thesis_management.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.web_apps.student_thesis_management.model.ThesisAssignmentRequest;
import com.web_apps.student_thesis_management.model.strategies.BestApplicantStrategy;
import com.web_apps.student_thesis_management.model.strategies.BestApplicantStrategyFactory;

@Service
public class ProfessorServiceImpl implements ProfessorService{

	@Autowired
	private ProfessorDAO professorRepository;
	
	@Autowired
	private SubjectDAO subjectRepository;
	
	@Autowired
	private StudentDAO studentRepository;
	
	@Autowired
	private ThesisDAO thesisRepository;
	
	@Autowired
	private ApplicationDAO applicationRepository;
	
	private BestApplicantStrategy strategy;
	
	@Override
	@Transactional
	public Professor findById(int theId) {
		Professor result = professorRepository.findById(theId);
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find professor id - " + theId);
		}
	}
	
	@Override
	@Transactional
	public void saveProfile(Professor theStudent) {
		professorRepository.save(theStudent);
	}
	
	@Override
	@Transactional
	public Professor findByUsername(String username) {
		Optional<Professor> professorOptional = professorRepository.findByUsername(username);
		Professor professor = professorOptional.orElseThrow(() -> new RuntimeException("Professor not found"));
		return professor;
	}
	
	@Override
	@Transactional
	public Professor getLoggedInProfessor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		Professor professor = this.findByUsername(username);
		Professor theProfessor = this.findById(professor.getProfessorId());
		return theProfessor;
	}
	
	@Override
	@Transactional
	public void saveSubject(Subject subject) {
		subjectRepository.save(subject);
	}
	
	@Override
	@Transactional
	public List<Subject> listProfessorSubjects(Professor professor){
		List<Subject> validSubjects = new ArrayList<Subject>();
		List<Subject> allSubjects = subjectRepository.findAll();
		for(Subject subject : allSubjects) {
			if(subject.getProfessorId()==professor.getProfessorId() && subject.getAvailability()) {
				validSubjects.add(subject);
			}
		}
		return validSubjects;
	}

	@Override
	@Transactional
	public List<Student> matchStudentsWithApplications(List<Application> applications) {
		List<Student> students = new ArrayList<Student>();
		for(Application application : applications) {
			students.add(studentRepository.findById(application.getStudentId()));
		}
		return students;
	}
	
	@Override
	@Transactional
	public int assignSubject(ThesisAssignmentRequest request){
		int thesisId = this.createThesis(request.getSubject()); //step 1
		int studentId = this.assignThesisToStudent(request, thesisId); //step 2
		if(studentId>0) {
			Subject subject = request.getSubject();
			subject.setAvailability(false);
			subjectRepository.save(subject);
			this.deleteStudentApplications(studentId); //step 3
		}else { //if no student fits the requirements we cancel the theses assignment
			System.out.println("Thesis id is: "+thesisId);
			this.deleteThesis(thesisId);
		}
		return studentId;
	}
	
	@Override
	@Transactional
	public List<Thesis> listProfessorTheses(Professor professor){
		List<Thesis> validTheses = new ArrayList<Thesis>();
		List<Thesis> allTheses= thesisRepository.findAll();
		for(Thesis thesis : allTheses) {
			if(thesis.getProfessorId()==professor.getProfessorId()) {
				validTheses.add(thesis);
			}
		}
		return validTheses;
	}
	
	private int createThesis(Subject subject) {
		Thesis thesis = new Thesis(subject.getName(), subject.getProfessorId(), subject.getDescription());
		thesisRepository.save(thesis);
		return thesis.getThesisId();
	}
	
	private void deleteThesis(int theId) {
		Thesis thesis = thesisRepository.findById(theId);
		thesisRepository.delete(thesis);
	}
	
	private int assignThesisToStudent(ThesisAssignmentRequest request, int thesisId) {
		Student bestApplicant = this.findBestApplicant(request);
		if(bestApplicant!=null) {
			bestApplicant.setThesisId(thesisId);
			studentRepository.save(bestApplicant);
			return bestApplicant.getStudentId();
		}else {
			return -1;
		}
	}
	
	private void deleteStudentApplications(int studentId) {
		List<Application> allApplications = applicationRepository.findAll();
		for(Application application : allApplications) {
			if(application.getStudentId() == studentId) {
				applicationRepository.delete(application);
			}
		}
	}
	
	public Student findBestApplicant(ThesisAssignmentRequest request) {
		BestApplicantStrategyFactory factory = new BestApplicantStrategyFactory();
		strategy = factory.createStrategy(request);
		Set<Student> uniqueStudents = new HashSet<Student>();
	    for (Application application : request.getApplications()) {
	        Student student = studentRepository.findById(application.getStudentId());
	        if (student != null && !uniqueStudents.contains(student)) {
	            uniqueStudents.add(student);
	        }
	    }
		return strategy.findBestApplicant(new ArrayList<>(uniqueStudents));
	}
}
