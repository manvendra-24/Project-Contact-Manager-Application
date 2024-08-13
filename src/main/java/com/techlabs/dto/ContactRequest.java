package com.techlabs.dto;


import jakarta.validation.constraints.NotNull;

public class ContactRequest {

	@NotNull(message = "First name is missing")
	private String firstName;
	
	@NotNull(message = "Last name is missing")
	private String lastName;

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

	public ContactRequest(@NotNull(message = "First name is missing") String firstName,
			@NotNull(message = "Last name is missing") String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public ContactRequest() {
		super();
	}
	
	
	
	
}
