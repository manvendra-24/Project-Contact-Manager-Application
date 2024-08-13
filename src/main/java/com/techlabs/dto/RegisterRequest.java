package com.techlabs.dto;

import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    
	@NotNull(message = "First name is missing")
    private String firstname;
    
    @NotNull(message = "Last name is missing")
    private String lastname;
    
    
    @NotNull(message = "Username is missing")
    private String username;
    
    @NotNull(message = "Email is missing")
    private String email;
    
    @NotNull(message = "Password is missing")
    private String password;

	public RegisterRequest(@NotNull(message = "First name is missing") String firstname,
			@NotNull(message = "Last name is missing") String lastname,
			@NotNull(message = "Username is missing") String username,
			@NotNull(message = "Email is missing") String email,
			@NotNull(message = "Password is missing") String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RegisterRequest() {
		super();
	}
    
    
}
