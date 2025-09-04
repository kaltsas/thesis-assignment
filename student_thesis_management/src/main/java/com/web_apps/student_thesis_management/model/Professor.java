package com.web_apps.student_thesis_management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="professors")
public class Professor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="professor_id")
	private int professorId;
	
	@Column(name="username", unique=true)
	private String username;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="specialty")
	private String specialty;
	
	public Professor(int professorId, String firstName, String lastName, String specialty) {
		super();
		this.professorId = professorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.specialty = specialty;
	}
	
	public Professor() {}

	public Professor(String username, String firstName, String lastName, String specialty) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.specialty = specialty;
	}
	
	public Professor(User user) {
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.specialty = "";
	}

	
	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	@Override
	public String toString() {
		return "ssor [professorId=" + professorId + ", username=" + username + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", specialty=" + specialty + "]";
	}

	
}
