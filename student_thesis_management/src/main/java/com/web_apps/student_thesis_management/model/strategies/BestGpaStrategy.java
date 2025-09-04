package com.web_apps.student_thesis_management.model.strategies;

import java.util.List;
import com.web_apps.student_thesis_management.model.Student;


public class BestGpaStrategy implements BestApplicantStrategy {
	
	public Student findBestApplicant(List<Student> students) {
		return chooseApplicantWithBestGpa(students);
	}
	
	public Student chooseApplicantWithBestGpa(List<Student> students) {
	    Student chosenApplicant = null;
	    double bestGpa = 0.0;
	    for (Student student : students) {
	        if (student != null && student.getGrade() > bestGpa) {
	            chosenApplicant = student;
	            bestGpa = student.getGrade();
	        }
	    }return chosenApplicant;
	}
}