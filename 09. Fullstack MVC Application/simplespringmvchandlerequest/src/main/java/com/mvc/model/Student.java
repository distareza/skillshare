package com.mvc.model;

import java.io.Serializable;

public class Student implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String major;
	
	public Student(String firstName, String lastName, String major) {
		this.id = (long) Math.floor(Math.random() * 1000000); 
		this.firstName = firstName;
		this.lastName = lastName;
		this.major = major;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Override
	public String toString() {
		return String.format("{ firstName = %s, lastName= %s, major= %s }", this.firstName, this.lastName, this.major);
	}	

}
