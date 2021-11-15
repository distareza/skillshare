package com.mvc.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Registration {

	@NotEmpty
	@Size(min = 6, max = 50, message = "Your name should not be empty and should be between 6 and 50 characters")
	private String name;
	
	@NotEmpty(message = "Please enter your email")
	@Email(message = "Your email address is not valid")
	private String email;
	
	@Max(value = 2, message = "You are noly allowed a maximum of 2 guests")
	private Integer numGuests;
	
	private String gender;
	private String department;
	private String[] food;

	public Registration() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getNumGuests() {
		return numGuests;
	}

	public void setNumGuests(Integer numGuests) {
		this.numGuests = numGuests;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String[] getFood() {
		return food;
	}

	public void setFood(String[] food) {
		this.food = food;
	}

	
}
