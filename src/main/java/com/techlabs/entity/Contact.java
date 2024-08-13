package com.techlabs.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "contacts")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "First name is missing")
	private String firstName;
	
	@NotNull(message = "Last name is missing")
	private String lastName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "staff_id")
	@JsonIgnore
	private Staff staff;
	
	@NotNull(message = "isActive is missing")
	private boolean isActive;
	
	@OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true, mappedBy = "contact")
	@JsonIgnore
	private List<ContactDetail> contactDetails;

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

	public List<ContactDetail> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<ContactDetail> contactDetails) {
		this.contactDetails = contactDetails;
	}

	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	
	
	public Contact(int id, @NotNull(message = "First name is missing") String firstName,
			@NotNull(message = "Last name is missing") String lastName, Staff staff,
			@NotNull(message = "isActive is missing") boolean isActive, List<ContactDetail> contactDetails) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.staff = staff;
		this.isActive = isActive;
		this.contactDetails = contactDetails;
	}

	public Contact() {
		super();
	}
	
	
	
}
