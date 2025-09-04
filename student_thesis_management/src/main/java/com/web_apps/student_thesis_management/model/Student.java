package com.web_apps.student_thesis_management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="students")
public class Student {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="student_id")
	private int studentId;
	
	@Column(name="username", unique=true)
	private String username;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="year")
	private int year;
	
	@Column(name="grade")
	private Double grade;
	
	@Column(name="remaining_courses")
	private int remainingCourses;
	
	@Column(name="graduated")
	private boolean graduate;
	
	@Column(name="thesis_id")
	private int thesisId;

	public Student(int studentId, String firstName, String lastName, int year, Double grade, int remainingCourses,
			int thesisId) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.year = year;
		this.grade = grade;
		this.remainingCourses = remainingCourses;
		this.thesisId = thesisId;
		this.graduate = false;
	}
	
	public Student() {}

	
	public Student(String username, String firstName, String lastName, int year, Double grade, int remainingCourses,
			boolean graduate, int thesisId) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.year = year;
		this.grade = grade;
		this.remainingCourses = remainingCourses;
		this.graduate = graduate;
		this.thesisId = thesisId;
	}

	public Student(User user) {
		super();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.year = -1;
		this.grade = (double) -1;
		this.remainingCourses = -1;
		this.thesisId = -1;
		this.graduate = false;
	}
	
	public boolean isGraduate() {
		return graduate;
	}

	public void setGraduate(boolean graduate) {
		this.graduate = graduate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public int getRemainingCourses() {
		return remainingCourses;
	}

	public void setRemainingCourses(int remainingCourses) {
		this.remainingCourses = remainingCourses;
	}

	public int getThesisId() {
		return thesisId;
	}

	public void setThesisId(int thesisId) {
		this.thesisId = thesisId;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", username=" + username + ", firstName=" + firstName + ", lastName="
				+ lastName + ", year=" + year + ", grade=" + grade + ", remainingCourses=" + remainingCourses
				+ ", graduate=" + graduate + ", thesisId=" + thesisId + "]";
	}

	

	
}
