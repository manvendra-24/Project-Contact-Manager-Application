package com.techlabs.dto;

import java.util.List;

import com.techlabs.entity.Contact;

public class CustomerResponse {

	private int id;
	private String firstName;
	private String lastName;
	private boolean isActive;
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
	public CustomerResponse() {
		super();
	}

	
}
