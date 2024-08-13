package com.techlabs.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	
	@NotBlank(message = "Username is missing")
	private String username;
	
	@NotBlank(message = "Password is missing")
	private String password;

	public LoginRequest() {
		super();
	}

	public LoginRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginDto [username =" + username + ", password=" + password + "]";
	}

}
