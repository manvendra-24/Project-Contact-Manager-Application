package com.techlabs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "admins")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "First name is missing")
	private String firstName;
	
	@NotNull(message = "Last name is missing")
	private String lastName;
	
	@NotNull(message = "isActive is missing")
	private boolean isActive;
	
	@OneToOne
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public Admin(int id, String firstName, String lastName, boolean isActive, User user) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isActive = isActive;
		this.user = user;
	}

	public Admin() {
		super();
	}

	
	
}
