package com.web_apps.student_thesis_management.model.strategies;

import java.util.List;
import com.web_apps.student_thesis_management.model.Student;


public class FewestRemainingCoursesStrategy implements BestApplicantStrategy {
	
	public Student findBestApplicant(List<Student> students) {
		return chooseApplicantWithFewestRemainingCourses(students);
	}
	
	public Student chooseApplicantWithFewestRemainingCourses(List<Student> students) {
		Student chosenApplicant = null;
		int leastRemainingCourses = Integer.MAX_VALUE;
	    for (Student student : students) {
	        if (student != null && student.getRemainingCourses() < leastRemainingCourses) {
	            chosenApplicant = student;
	            leastRemainingCourses = student.getRemainingCourses();
	        }
	    }return chosenApplicant;
	}
}