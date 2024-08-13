package com.techlabs.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "staffs")
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "First Name is missing")
	private String firstName;
	
	@NotNull(message = "Last Name is missing")
	private String lastName;
	
	@NotNull(message = "IsActive is missing")
	private boolean isActive;
	
	@OneToOne
	private User user;
	
	@OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true, mappedBy = "staff")
	@JsonIgnore
	private List<Contact> contacts;

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

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public Staff(int id, String firstName, String lastName, boolean isActive, User user, List<Contact> contacts) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isActive = isActive;
		this.user = user;
		this.contacts = contacts;
	}

	public Staff() {
		super();
	}
	
	
}
