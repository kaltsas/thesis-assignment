package com.web_apps.student_thesis_management.model.strategies;

import java.util.ArrayList;
import java.util.List;
import com.web_apps.student_thesis_management.model.Student;


public class ThresholdStrategy implements BestApplicantStrategy {
	
	private RandomChoiseStrategy randomStrategy;
	private int maxNumberOfTRemainingCourses;
	private double leastGpa;
	
	public ThresholdStrategy(int maxNumberOfTRemainingCourses, double leastGpa) {
		randomStrategy = new RandomChoiseStrategy();
		this.maxNumberOfTRemainingCourses = maxNumberOfTRemainingCourses;
		this.leastGpa = leastGpa;
	}

	public Student findBestApplicant(List<Student> students) {
		return chooseApplicantWithThreshold(students);
	}
	
	//choose random from the valid applicants
	public Student chooseApplicantWithThreshold(List<Student> students) {
		List<Student> validApplicants = this.getValidStudents(students);
		return randomStrategy.findBestApplicant(validApplicants);
	}
	
	//this method filters the application based on the threshold restrictions
	public List<Student> getValidStudents(List<Student> students) {
		List<Student> validApplicants = new ArrayList<Student>();
	    for (Student student : students) {
	        if (student != null && student.getRemainingCourses() <= maxNumberOfTRemainingCourses
	                && student.getGrade() >= leastGpa) {
	        	validApplicants.add(student);
	        }
	    }return validApplicants;
	}
}
