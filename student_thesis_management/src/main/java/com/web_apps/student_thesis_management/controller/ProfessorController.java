package com.web_apps.student_thesis_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web_apps.student_thesis_management.model.Application;
import com.web_apps.student_thesis_management.model.Professor;
import com.web_apps.student_thesis_management.model.Student;
import com.web_apps.student_thesis_management.model.Subject;
import com.web_apps.student_thesis_management.model.Thesis;
import com.web_apps.student_thesis_management.model.ThesisAssignmentRequest;
import com.web_apps.student_thesis_management.service.ProfessorService;
import com.web_apps.student_thesis_management.service.StudentService;
import com.web_apps.student_thesis_management.service.SubjectService;
import com.web_apps.student_thesis_management.service.ThesisService;

@Controller
public class ProfessorController {
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private ThesisService thesisService;

	@RequestMapping("/professor/dashboard")
    public String getProfessorHome(){
        return "professor/dashboard";
    }
	
	@RequestMapping("/professor/showFormForUpdate")
	public String showFormForUpdate(Model theModel) {
		theModel.addAttribute("professor", professorService.getLoggedInProfessor());
		return "professor/professor-form";			
	}
	
	@RequestMapping("/professor/save")
	public String saveProfessor(@ModelAttribute("professor") Professor theProfessor) {
		professorService.saveProfile(theProfessor);
		return "redirect:/professor/dashboard";
	}
	
	@RequestMapping("/professor/showFormToAddSubject")
	public String showFormForSubject(Model theModel) {
		Professor theProfessor = professorService.getLoggedInProfessor();
		theModel.addAttribute("subject", new Subject(theProfessor.getProfessorId()));
		return "professor/new-subject-form";
	}
	
	@RequestMapping("/professor/showFormToUpdateSubject")
	public String updateSubject(@RequestParam("subjectId") int theId, Model theModel) {
		Subject theSubject = subjectService.findById(theId);
		theModel.addAttribute("subject", theSubject);
		return "professor/new-subject-form";
	}
	
	@RequestMapping("/professor/showSubjects")
	public String listProfessorSubjects(Model theModel) {
		Professor theProfessor = professorService.getLoggedInProfessor();
		List<Subject> validSubjects = professorService.listProfessorSubjects(theProfessor);
		theModel.addAttribute("subjects", validSubjects);
		return "professor/subjects";
	}
	
	@RequestMapping("/professor/addSubject")
	public String addSubject(@ModelAttribute("subject") Subject theSubject) {
		professorService.saveSubject(theSubject);
		return "redirect:/professor/showSubjects";
	}
	
	@RequestMapping("professor/deleteSubject")
	public String deleteSubject(@RequestParam("subjectId") int theId) {
		subjectService.deleteById(theId);
		return "redirect:/professor/showSubjects";
	}
	
	@RequestMapping("professor/checkApplications")
	public String checkApplications(@RequestParam("subjectId") int theId, Model theModel) {
		List<Application> applications = subjectService.getApplicationsOfSubject(theId);
		List<Student> students = professorService.matchStudentsWithApplications(applications);
		Subject subject = subjectService.findById(theId);
		theModel.addAttribute("applications", applications);
		theModel.addAttribute("students", students);
		theModel.addAttribute("subject", subject);
		return "/professor/subject-applications";
	}
	
	@RequestMapping("professor/assignSubject")
	public String assignSubject(@RequestParam("flag") int flag, @RequestParam("numberOfRemainingCourses") int numberOfRemainingCourses,
			@RequestParam("grade") double grade, @RequestParam("subjectId") int subjectId) {
		Subject subject = subjectService.findById(subjectId);
		List<Application> applications = subjectService.getApplicationsOfSubject(subjectId);
		if(!(applications.size()>0)) {
			return "professor/show-fail-message";
		}
		ThesisAssignmentRequest request = new ThesisAssignmentRequest(applications, flag,
		numberOfRemainingCourses, grade, subject);
		int studentId = professorService.assignSubject(request);
		if(studentId>0) {
			return "redirect:/professor/showSubjects";	
		}else {
			return "professor/show-fail-message";
		}
	}
	
	@RequestMapping("professor/showTheses")
	public String prepareTheses(Model theModel) {
		Professor theProfessor = professorService.getLoggedInProfessor();
		List<Thesis> validTheses = professorService.listProfessorTheses(theProfessor);
		List<Student> students = studentService.matchStudentsWithTheses(validTheses);
		theModel.addAttribute("theses", validTheses);
		theModel.addAttribute("students", students);
		return "professor/theses";
	}
	
	@RequestMapping("professor/rateThesis")
	public String prepareThesis(@RequestParam("thesisId") int theId, Model theModel) {
		Thesis theThesis = thesisService.findById(theId);
		theModel.addAttribute("thesis", theThesis);
		return "professor/thesis-form";
	}
	
	@RequestMapping("professor/calculateFinalGrade")
	public String calculateGrade(@ModelAttribute("thesis") Thesis theThesis, Model theModel) {
		thesisService.calculateFinalGrade(theThesis);
		return "professor/dashboard";
	}
}
