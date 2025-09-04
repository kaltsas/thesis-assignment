package com.web_apps.student_thesis_management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="theses")
public class Thesis {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="thesis_id")
	private int thesisId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="professor_id")
	private int professorId;
	
	@Column(name="description")
	private String description;
	
	@Column(name="implementation_grade")
	private Double implementationGrade;
	
	@Column(name="report_grade")
	private Double reportGrade;
	
	@Column(name="presentation_grade")
	private Double presentationGrade;
	
	@Column(name="final_grade")
	private Double finalGrade;

	public Thesis(int thesisId, String title, int professorId, String description, Double implementationGrade,
			Double reportGrade, Double presentationGrade, Double finalGrade) {
		super();
		this.thesisId = thesisId;
		this.title = title;
		this.professorId = professorId;
		this.description = description;
		this.implementationGrade = implementationGrade;
		this.reportGrade = reportGrade;
		this.presentationGrade = presentationGrade;
		this.finalGrade = finalGrade;
	}

	public Thesis(String title, int professorId, String description, Double implementationGrade, Double reportGrade,
			Double presentationGrade, Double finalGrade) {
		super();
		this.title = title;
		this.professorId = professorId;
		this.description = description;
		this.implementationGrade = implementationGrade;
		this.reportGrade = reportGrade;
		this.presentationGrade = presentationGrade;
		this.finalGrade = finalGrade;
	}
	
	public Thesis() {}
	
	

	public Thesis(String title, int professorId, String description) {
		super();
		this.title = title;
		this.professorId = professorId;
		this.description = description;
		this.implementationGrade = -1.0;
		this.reportGrade = -1.0;
		this.presentationGrade = -1.0;
		this.finalGrade = -1.0;
	}

	public int getThesisId() {
		return thesisId;
	}

	public void setThesisId(int thesisId) {
		this.thesisId = thesisId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getImplementationGrade() {
		return implementationGrade;
	}

	public void setImplementationGrade(Double implementationGrade) {
		this.implementationGrade = implementationGrade;
	}

	public Double getReportGrade() {
		return reportGrade;
	}

	public void setReportGrade(Double reportGrade) {
		this.reportGrade = reportGrade;
	}

	public Double getPresentationGrade() {
		return presentationGrade;
	}

	public void setPresentationGrade(Double presentationGrade) {
		this.presentationGrade = presentationGrade;
	}

	public Double getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(Double finalGrade) {
		this.finalGrade = finalGrade;
	}

	@Override
	public String toString() {
		return "Thesis [thesisId=" + thesisId + ", title=" + title + ", professorId=" + professorId + ", description="
				+ description + ", implementationGrade=" + implementationGrade + ", reportGrade=" + reportGrade
				+ ", presentationGrade=" + presentationGrade + ", finalGrade=" + finalGrade + "]";
	}
	
	
}
