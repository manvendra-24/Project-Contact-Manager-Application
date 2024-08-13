package com.techlabs.dto;

import com.techlabs.entity.Contact;

import jakarta.validation.constraints.NotNull;

public class ContactDetailResponse {

	@NotNull(message = "Contact is missing")
	private Contact contact;
	
	@NotNull(message = "Contact Detail type is missing")
	private String type;
	
	@NotNull(message = "Contact Detail PhoneNumber or EmailId is missing")
	private String numberOrEmail;

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumberOrEmail() {
		return numberOrEmail;
	}

	public void setNumberOrEmail(String numberOrEmail) {
		this.numberOrEmail = numberOrEmail;
	}

	public ContactDetailResponse() {
		super();
	}
	
	
}
