package com.web_apps.student_thesis_management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="subjects")
public class Subject {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="subject_id")
	private int subjectId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="professor_id")
	private int professorId;
	
	@Column(name="description")
	private String description;
	
	@Column(name="availability")
	private Boolean availability;
	
	@Column(name="num_semesters")
	private int numberOfSemesters;

	public Subject(int subjectId, String name, int professorId, String description, Boolean availability,
			int numberOfSemesters) {
		super();
		this.subjectId = subjectId;
		this.name = name;
		this.professorId = professorId;
		this.description = description;
		this.availability = availability;
		this.numberOfSemesters = numberOfSemesters;
	}

	public Subject(String name, int professorId, String description, Boolean availability, int numberOfSemesters) {
		super();
		this.name = name;
		this.professorId = professorId;
		this.description = description;
		this.availability = availability;
		this.numberOfSemesters = numberOfSemesters;
	}
	
	public Subject() {}
	
	public Subject(int professorId) {
		super();
		this.name = "";
		this.professorId = professorId;
		this.description = "";
		this.availability = true;
		this.numberOfSemesters = 1;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public int getNumberOfSemesters() {
		return numberOfSemesters;
	}

	public void setNumberOfSemesters(int numberOfSemesters) {
		this.numberOfSemesters = numberOfSemesters;
	}

	@Override
	public String toString() {
		return "Subject [subjectId=" + subjectId + ", name=" + name + ", professorId=" + professorId + ", description="
				+ description + ", availability=" + availability + ", numberOfSemesters=" + numberOfSemesters + "]";
	}
}
