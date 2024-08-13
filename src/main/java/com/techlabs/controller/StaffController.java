package com.techlabs.controller;


import java.nio.file.AccessDeniedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.dto.ContactDetailRequest;
import com.techlabs.dto.ContactDetailResponse;
import com.techlabs.dto.ContactRequest;
import com.techlabs.dto.ContactResponse;
import com.techlabs.exception.ApiException;
import com.techlabs.security.JwtTokenProvider;
import com.techlabs.service.IService;
import com.techlabs.util.PagedResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class StaffController {

	private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

	@Autowired
	IService service;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	//add a contact
		@PostMapping("/contacts")
		public ResponseEntity<ContactResponse> createAContact(HttpServletRequest request,@RequestBody ContactRequest contactRequest){
			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
		        String token = authorizationHeader.substring(7);
		        String customerId = jwtTokenProvider.getUsername(token);
		        
		        logger.info("Customer with CustomerId: "+ customerId +" trying to create a new contact");
		        

		        ContactResponse contactResponse = service.createAContact(customerId, contactRequest);
				return new ResponseEntity<>(contactResponse ,HttpStatus.OK);
			}
			throw new ApiException("Unauthorized");
		}
		
		//get all contacts
		@GetMapping("/contacts")
		public ResponseEntity<PagedResponse<ContactResponse>> getAllContacts(
				HttpServletRequest request,
				@RequestParam(value = "page", defaultValue = "0") int page,
		        @RequestParam(value = "size", defaultValue = "10") int size,
		        @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
		        @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
			
			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
		        String token = authorizationHeader.substring(7);
		        String customerId = jwtTokenProvider.getUsername(token);
		        logger.info("Customer "+ customerId +" trying to get all contacts");
		        PagedResponse<ContactResponse> contactResponses = service.getAllContacts(customerId ,page, size, sortBy, direction);
		        return new ResponseEntity<>(contactResponses, HttpStatus.OK);
			}
			
			throw new ApiException("Unauthorized");
		}
		
		//get contacts by id
		@GetMapping("/contacts/{id}")
		public ResponseEntity<ContactResponse> getContactById(HttpServletRequest request, @PathVariable int id){
			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
		        String token = authorizationHeader.substring(7);
		        String customerId = jwtTokenProvider.getUsername(token);
		        logger.info("Customer "+ customerId +" trying to get a contact with id: " + id);
		        ContactResponse contactResponse = service.getContactById(customerId, id);
		        return new ResponseEntity<>(contactResponse, HttpStatus.OK);
			}
			
			throw new ApiException("Unauthorized");
		}
		
		//update contact by id
		@PutMapping("/contacts/{id}")
		public ResponseEntity<String> reactivateContact(HttpServletRequest request, @PathVariable int id) throws AccessDeniedException{
			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
		        String token = authorizationHeader.substring(7);
		        String customerId = jwtTokenProvider.getUsername(token);
		        logger.info("Customer "+ customerId +" trying to reactivate a customer with id " + id);
				service.reactivateContact(customerId, id);
		        return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
			}
			throw new ApiException("Unauthorized");
		}
		
		
		//delete customer
		@DeleteMapping("/contacts/{id}")
		public ResponseEntity<String> deleteContact(HttpServletRequest request, @PathVariable int id) throws AccessDeniedException {
			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
		        String token = authorizationHeader.substring(7);
		        String customerId = jwtTokenProvider.getUsername(token);
		        logger.info("Customer "+ customerId +" trying to delete a customer with id " + id);
				service.deleteContact(customerId, id);
		        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
			}
			throw new ApiException("Unauthorized");
		}
		
		
		
		
		//add a contact details
		@PostMapping("/contactDetails")
		public ResponseEntity<ContactDetailResponse> createAContactDetail(HttpServletRequest request,@RequestBody ContactDetailRequest contactDetailRequest){
			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				  String token = authorizationHeader.substring(7);
				  String customerId = jwtTokenProvider.getUsername(token);
				        
				  logger.info("Customer with CustomerId: "+ customerId +" trying to add a new contact detail");
				  ContactDetailResponse contactDetailResponse = service.createAContactDetail(customerId, contactDetailRequest);
				  return new ResponseEntity<>(contactDetailResponse ,HttpStatus.OK);
			}
			throw new ApiException("Unauthorized");
		}
				
		
		
	
				
				//get contact details by id
				@GetMapping("/contactDetails/{id}")
				public ResponseEntity<ContactDetailResponse> getContactDetailById(HttpServletRequest request, @PathVariable int id){
					String authorizationHeader = request.getHeader("Authorization");
					if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				        String token = authorizationHeader.substring(7);
				        String customerId = jwtTokenProvider.getUsername(token);
				        logger.info("Customer "+ customerId +" trying to get a contact with id: " + id);
				        ContactDetailResponse contactDetailResponse = service.getContactDetailById(customerId, id);
				        return new ResponseEntity<>(contactDetailResponse, HttpStatus.OK);
					}
					
					throw new ApiException("Unauthorized");
				}

}
