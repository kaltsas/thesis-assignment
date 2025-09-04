package com.web_apps.student_thesis_management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="applications")
public class Application {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="application_id")
	private int applicationId;
	
	@Column(name="student_id")
	private int studentId;
	
	@Column(name="subject_id")
	private int subjectId;
	
	@Column(name="message")
	private String message;

	public Application(int applicationId, int studentId, int subjectId, String message) {
		super();
		this.applicationId = applicationId;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.message = message;
	}

	public Application(int studentId, int subjectId, String message) {
		super();
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.message = message;
	}

	public Application() {}
	
	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Application [applicationId=" + applicationId + ", studentId=" + studentId + ", subjectId="
				+ subjectId + ", message=" + message + "]";
	}
}
