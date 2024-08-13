package com.techlabs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "contactDetails")
public class ContactDetail {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
		@NotNull(message = "Contact Detail type is missing")
		private String type;
		
		@NotNull(message = "Contact Detail PhoneNumber or EmailId is missing")
		private String numberOrEmail;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "contact_id")
		@JsonIgnore
		private Contact contact;
		
		@NotNull(message = "Contact Detail isActive is missing")
		private boolean isActive;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
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

		public Contact getContact() {
			return contact;
		}

		public void setContact(Contact contact) {
			this.contact = contact;
		}
		
		

		public boolean isActive() {
			return isActive;
		}

		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}

		public ContactDetail(int id, String type, String numberOrEmail, Contact contact) {
			super();
			this.id = id;
			this.type = type;
			this.numberOrEmail = numberOrEmail;
			this.contact = contact;
		}

		public ContactDetail() {
			super();
		}
		
		
}
