package com.web_apps.student_thesis_management.model;

import java.util.List;

public class ThesisAssignmentRequest {
	
	private List<Application> applications;
	private int strategyMethodFlag;
	private int numberOfRemainingCoursesThreshold;
	private double gradeThreshold;
	private Subject subject;
	
	public ThesisAssignmentRequest(List<Application> applications, int strategyMethodFlag,
			int numberOfRemainingCoursesThreshold, double gradeThreshold, Subject subject) {
		super();
		this.applications = applications;
		this.strategyMethodFlag = strategyMethodFlag;
		this.numberOfRemainingCoursesThreshold = numberOfRemainingCoursesThreshold;
		this.gradeThreshold = gradeThreshold;
		this.subject = subject;
	}
	
	public List<Application> getApplications() {
		return applications;
	}
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	public int getStrategyMethodFlag() {
		return strategyMethodFlag;
	}
	public void setStrategyMethodFlag(int strategyMethodFlag) {
		this.strategyMethodFlag = strategyMethodFlag;
	}
	public int getNumberOfRemainingCoursesThreshold() {
		return numberOfRemainingCoursesThreshold;
	}
	public void setNumberOfRemainingCoursesThreshold(int numberOfRemainingCoursesThreshold) {
		this.numberOfRemainingCoursesThreshold = numberOfRemainingCoursesThreshold;
	}
	public double getGradeThreshold() {
		return gradeThreshold;
	}
	public void setGradeThreshold(double gradeThreshold) {
		this.gradeThreshold = gradeThreshold;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
