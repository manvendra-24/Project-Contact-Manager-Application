package com.techlabs.service;

import java.nio.file.AccessDeniedException;

import org.springframework.stereotype.Service;

import com.techlabs.dto.ContactDetailRequest;
import com.techlabs.dto.ContactDetailResponse;
import com.techlabs.dto.ContactRequest;
import com.techlabs.dto.ContactResponse;
import com.techlabs.dto.CustomerResponse;
import com.techlabs.dto.RegisterRequest;
import com.techlabs.dto.UserResponse;
import com.techlabs.util.PagedResponse;

@Service
public interface IService {

	UserResponse createACustomer(RegisterRequest registerRequest);
	PagedResponse<CustomerResponse> getAllCustomers(int page, int size, String sortBy, String direction);
	CustomerResponse getCustomerById(int id);
	void reactivateCustomer(int id) throws AccessDeniedException;
	void deleteCustomer(int id) throws AccessDeniedException;
	
	
	

	PagedResponse<ContactResponse> getAllContacts(String customerId, int page, int size, String sortBy, String direction);
	ContactResponse createAContact(String customerId, ContactRequest contactRequest);
	ContactResponse getContactById(String customerId, int id);
	void reactivateContact(String customerId, int id) throws AccessDeniedException;
	void deleteContact(String customerId, int id) throws AccessDeniedException;

	ContactDetailResponse createAContactDetail(String customerId, ContactDetailRequest contactDetailRequest);
	ContactDetailResponse getContactDetailById(String customerId, int id);

	
}
