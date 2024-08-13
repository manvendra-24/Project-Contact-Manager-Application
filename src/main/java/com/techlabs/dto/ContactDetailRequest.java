package com.techlabs.dto;


import jakarta.validation.constraints.NotNull;

public class ContactDetailRequest {
	
	@NotNull(message = "Contact Id is missing")
	private int contact_id;
	
	@NotNull(message = "Contact Detail type is missing")
	private String type;
	
	@NotNull(message = "Contact Detail PhoneNumber or EmailId is missing")
	private String numberOrEmail;

	

	public ContactDetailRequest() {
		super();
	}

	
	public int getContact_id() {
		return contact_id;
	}


	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
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
	
	
}
