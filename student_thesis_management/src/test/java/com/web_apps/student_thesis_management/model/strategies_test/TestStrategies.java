package com.web_apps.student_thesis_management.model.strategies_test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.web_apps.student_thesis_management.model.Student;
import com.web_apps.student_thesis_management.model.strategies.BestGpaStrategy;
import com.web_apps.student_thesis_management.model.strategies.FewestRemainingCoursesStrategy;
import com.web_apps.student_thesis_management.model.strategies.RandomChoiseStrategy;
import com.web_apps.student_thesis_management.model.strategies.ThresholdStrategy;


@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class TestStrategies {

	static BestGpaStrategy gpaStrategy;
	static FewestRemainingCoursesStrategy coursesStrategy;
	static RandomChoiseStrategy randomStrategy;
	static ThresholdStrategy thresholdStrategy;
	
	static List<Student> students;
		
	@BeforeAll
	static void initialize() {
		gpaStrategy = new BestGpaStrategy();
		coursesStrategy = new FewestRemainingCoursesStrategy();
		randomStrategy = new RandomChoiseStrategy();
		thresholdStrategy = new ThresholdStrategy(1, 9.5);
		students = new ArrayList<Student>();
	}
	
	@Test
	void testGpaStrategy() {
		students.add(new Student(1, "jhon", "moors", 2018, 7.88, 2, -1));
		students.add(new Student(2, "george", "lee", 2017, 9.3, 3, -1));
		students.add(new Student(3, "jonathan", "scether", 2020, 6.34, 0, -1));
		Student result = gpaStrategy.findBestApplicant(students);
		Assertions.assertEquals(2, result.getStudentId());
	}
	
	@Test
	void testRemainingCoursesStrategy() {
		students.add(new Student(1, "jhon", "moors", 2018, 7.88, 2, -1));
		students.add(new Student(2, "george", "lee", 2017, 9.3, 3, -1));
		students.add(new Student(3, "jonathan", "scether", 2020, 6.34, 0, -1));
		Student result = coursesStrategy.findBestApplicant(students);
		Assertions.assertEquals(3, result.getStudentId());
	}
	
	@Test
	void testRandomStrategy() {
		students.add(new Student(1, "jhon", "moors", 2018, 7.88, 2, -1));
		students.add(new Student(2, "george", "lee", 2017, 9.3, 3, -1));
		students.add(new Student(3, "jonathan", "scether", 2020, 6.34, 0, -1));
		Student result = randomStrategy.findBestApplicant(students);
		Assertions.assertNotNull(result);
	}
	
	@Test
	void testThreshHoldStrategy() {
		students.add(new Student(1, "jhon", "moors", 2018, 7.88, 2, -1));
		students.add(new Student(2, "george", "lee", 2017, 9.3, 3, -1));
		students.add(new Student(3, "jonathan", "scether", 2020, 6.34, 0, -1));
		Student result = thresholdStrategy.findBestApplicant(students);
		Assertions.assertNull(result);
	}
}
