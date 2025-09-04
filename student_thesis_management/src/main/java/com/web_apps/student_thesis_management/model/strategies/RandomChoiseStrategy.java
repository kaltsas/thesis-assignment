package com.web_apps.student_thesis_management.model.strategies;

import java.util.List;
import java.util.Random;
import com.web_apps.student_thesis_management.model.Student;


public class RandomChoiseStrategy implements BestApplicantStrategy {
	
	public Student findBestApplicant(List<Student> students) {
		return chooseRandomApplicant(students);
	}
	
	public Student chooseRandomApplicant(List<Student> students) {
		if(!(students.size()>0)){/*		there are students		*/
			return null;
		}
		Random random = new Random();
		int numberOfApplicants = students.size();
		int randomIndex = random.nextInt(numberOfApplicants);
		return students.get(randomIndex);
	}
}
