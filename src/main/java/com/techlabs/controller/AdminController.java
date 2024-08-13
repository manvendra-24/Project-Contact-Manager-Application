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

import com.techlabs.controller.AdminController;
import com.techlabs.dto.CustomerResponse;
import com.techlabs.dto.RegisterRequest;
import com.techlabs.dto.UserResponse;
import com.techlabs.exception.ApiException;
import com.techlabs.security.JwtTokenProvider;
import com.techlabs.service.IService;
import com.techlabs.util.PagedResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class AdminController {

	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	IService service;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	//add a staff
	@PostMapping("/users")
	public ResponseEntity<UserResponse> createACustomer(HttpServletRequest request,@RequestBody RegisterRequest registerRequest){
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        String token = authorizationHeader.substring(7);
	        String adminId = jwtTokenProvider.getUsername(token);
	        
	        logger.info("Admin with AdminId: "+ adminId +" trying to create a new customer");
	        System.out.println("Admin with AdminId: "+ adminId +" trying to create a new customer" + registerRequest.getFirstname());

	        UserResponse userResponse = service.createACustomer(registerRequest);
			return new ResponseEntity<>(userResponse ,HttpStatus.OK);
		}
		throw new ApiException("Unauthorized");
	}
	
	//get all users
	@GetMapping("/users")
	public ResponseEntity<PagedResponse<CustomerResponse>> getAllCustomers(
			HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
	        @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        String token = authorizationHeader.substring(7);
	        String adminId = jwtTokenProvider.getUsername(token);
	        logger.info("Admin "+ adminId +" trying to get all customers");
	        PagedResponse<CustomerResponse> customersResponse = service.getAllCustomers(page, size, sortBy, direction);
	        return new ResponseEntity<>(customersResponse, HttpStatus.OK);
		}
		
		throw new ApiException("Unauthorized");
	}
	
	//get user by id
	@GetMapping("/users/{id}")
	public ResponseEntity<CustomerResponse> getCustomerById(HttpServletRequest request, @PathVariable int id){
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        String token = authorizationHeader.substring(7);
	        String adminId = jwtTokenProvider.getUsername(token);
	        logger.info("Admin "+ adminId +" trying to get a customer with id " + id);
	        CustomerResponse customerResponse = service.getCustomerById(id);
	        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
		}
		
		throw new ApiException("Unauthorized");
	}
	
	//update user
	@PutMapping("/users/{id}")
	public ResponseEntity<String> updateCustomer(HttpServletRequest request, @PathVariable int id){
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        String token = authorizationHeader.substring(7);
	        String adminId = jwtTokenProvider.getUsername(token);
	        logger.info("Admin "+ adminId +" trying to reactivate a customer with id " + id);
	        try {
				service.reactivateCustomer(id);
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
		}
		throw new ApiException("Unauthorized");
	}
	
	
	//delete user
	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteCustomer(HttpServletRequest request, @PathVariable int id){
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        String token = authorizationHeader.substring(7);
	        String adminId = jwtTokenProvider.getUsername(token);
	        logger.info("Admin "+ adminId +" trying to delete a customer with id " + id);
	        try {
				service.deleteCustomer(id);
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
		}
		
		throw new ApiException("Unauthorized");
	}
	
}
