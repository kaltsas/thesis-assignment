package com.web_apps.student_thesis_management.model.strategies;

import com.web_apps.student_thesis_management.model.Student;

import java.util.List;

public interface BestApplicantStrategy {
	public Student findBestApplicant(List<Student> students);
}
