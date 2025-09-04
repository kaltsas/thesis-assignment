package com.web_apps.student_thesis_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.web_apps.student_thesis_management.model.Student;
import com.web_apps.student_thesis_management.model.Subject;
import com.web_apps.student_thesis_management.model.Thesis;
import com.web_apps.student_thesis_management.service.StudentService;
import com.web_apps.student_thesis_management.service.SubjectService;
import com.web_apps.student_thesis_management.service.ThesisService;


@Controller
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ThesisService thesisService;
	
	@RequestMapping("/student/dashboard")
    public String getUserHome(Model model){
        return "student/dashboard";
    }
	
	@RequestMapping("/student/showFormForUpdate")
	public String showFormForUpdate(Model theModel) {
		theModel.addAttribute("student", studentService.getLoggedInStudent());
		return "student/student-form";	
	}
	
	@RequestMapping("/student/save")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveProfile(student);
		return "redirect:/student/dashboard";
	}
	
	@RequestMapping("/student/showSubjects")
	public String listStudentSubjects(Model theModel) {
		List<Subject> availableSubjects = subjectService.listAvailableSubjects();
		theModel.addAttribute("subjects", availableSubjects);
		return "student/subjects";
	}
	
	@RequestMapping("/student/showFormToApplyForSubject")
	public String prepareForm(@ModelAttribute("subjectId") int theId, Model theModel) {
		theModel.addAttribute("student", studentService.getLoggedInStudent());
		theModel.addAttribute("subject", subjectService.findById(theId));
		theModel.addAttribute("professor", subjectService.getSupervisor(theId));
		return "student/new-application-form";
	}
	
	@RequestMapping("/student/applyForSubject")
	public String applyForSubject(@ModelAttribute("subjectId") int theId,  @ModelAttribute("message") String message) {	
		Student theStudent = studentService.getLoggedInStudent();
		studentService.applyForSubject(theStudent.getStudentId(), theId, message);
		return "redirect:/student/dashboard";
	}
	
	@RequestMapping("/student/showThesis")
	public String showThesis(Model theModel) {
		Student theStudent = studentService.getLoggedInStudent();
		Thesis theThesis = thesisService.findById(theStudent.getThesisId());
		theModel.addAttribute("student", theStudent);
		theModel.addAttribute("thesis", theThesis);
		return "student/thesis";
	}
	
	@RequestMapping("/student/deleteThesis")
	public String deleteThesis(Model theModel) {
		Student theStudent = studentService.getLoggedInStudent();
		boolean success = studentService.deleteThesis(theStudent);
		if(success) {
			return "student/success";
		}else {
			return "student/fail";
		}
	}
}