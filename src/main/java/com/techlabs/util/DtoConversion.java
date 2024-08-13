package com.techlabs.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techlabs.dto.ContactDetailRequest;
import com.techlabs.dto.ContactDetailResponse;
import com.techlabs.dto.ContactRequest;
import com.techlabs.dto.ContactResponse;
import com.techlabs.dto.CustomerResponse;
import com.techlabs.entity.Contact;
import com.techlabs.entity.ContactDetail;
import com.techlabs.entity.Staff;
import com.techlabs.repository.ContactRepository;

@Component
public class DtoConversion {

	@Autowired
	ContactRepository contactRepository;
	
	public CustomerResponse convertCustomerToResponse(Staff customer) {
		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setId(customer.getId());
		customerResponse.setFirstName(customer.getFirstName());
		customerResponse.setLastName(customer.getLastName());
		customerResponse.setActive(customer.isActive());
		customerResponse.setContacts(customer.getContacts());
		return customerResponse;
	}
	
	public ContactResponse convertContactToResponse(Contact contact) {
		ContactResponse contactResponse = new ContactResponse();
		
		contactResponse.setId(contact.getId());
		contactResponse.setFirstName(contact.getFirstName());
		contactResponse.setLastName(contact.getLastName());
		contactResponse.setActive(contact.isActive());
		contactResponse.setContactDetails(contact.getContactDetails());
		return contactResponse;
	}
	
	public Contact convertRequestToContact(ContactRequest contactRequest) {
		Contact contact  = new Contact();
		
		contact.setFirstName(contactRequest.getFirstName());
		contact.setLastName(contactRequest.getLastName());
		contact.setActive(true);
		
		return contact;
	}

	public ContactDetailResponse convertContactDetailToResponse(ContactDetail contactDetail) {
		ContactDetailResponse contactDetailResponse = new ContactDetailResponse();
		
		contactDetailResponse.setType(contactDetail.getType());
		contactDetailResponse.setNumberOrEmail(contactDetail.getNumberOrEmail());
		contactDetailResponse.setContact(contactDetail.getContact());
		
		return contactDetailResponse;
	}

	public ContactDetail convertRequestToContactDetail(ContactDetailRequest contactDetailRequest) {
		ContactDetail contactDetail = new ContactDetail();
		Contact contact = contactRepository.findById(contactDetailRequest.getContact_id()).get();
		contactDetail.setActive(true);
		contactDetail.setNumberOrEmail(contactDetailRequest.getNumberOrEmail());
		contactDetail.setType(contactDetailRequest.getType());
		contactDetail.setContact(contact);
		
		return contactDetail;
	}

	
}
