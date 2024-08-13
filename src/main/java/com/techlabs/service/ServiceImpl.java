package com.techlabs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.controller.AdminController;
import com.techlabs.dto.ContactDetailRequest;
import com.techlabs.dto.ContactDetailResponse;
import com.techlabs.dto.ContactRequest;
import com.techlabs.dto.ContactResponse;
import com.techlabs.dto.CustomerResponse;
import com.techlabs.dto.RegisterRequest;
import com.techlabs.dto.UserResponse;
import com.techlabs.entity.Contact;
import com.techlabs.entity.ContactDetail;
import com.techlabs.entity.Role;
import com.techlabs.entity.Staff;
import com.techlabs.entity.User;
import com.techlabs.exception.AlreadyReportedException;
import com.techlabs.exception.ApiException;
import com.techlabs.exception.ResourceNotFoundException;
import com.techlabs.repository.ContactDetailRepository;
import com.techlabs.repository.ContactRepository;
import com.techlabs.repository.RoleRepository;
import com.techlabs.repository.StaffRepository;
import com.techlabs.repository.UserRepository;
import com.techlabs.util.DtoConversion;
import com.techlabs.util.PagedResponse;


@Service
public class ServiceImpl implements IService {

	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	StaffRepository customerRepository;
	UserRepository userRepository;
	RoleRepository roleRepository;
	ContactRepository contactRepository;
	ContactDetailRepository contactDetailRepository;
	DtoConversion dtoConversion;
	PasswordEncoder passwordEncoder;
	
	
	
	public ServiceImpl(StaffRepository customerRepository, UserRepository userRepository, RoleRepository roleRepository,
			ContactRepository contactRepository, ContactDetailRepository contactDetailRepository,
			DtoConversion dtoConversion, PasswordEncoder passwordEncoder) {
		super();
		this.customerRepository = customerRepository;
		this.contactDetailRepository = contactDetailRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.contactRepository = contactRepository;
		this.dtoConversion = dtoConversion;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserResponse createACustomer(RegisterRequest registerRequest) {
		
		System.out.println(registerRequest.getFirstname());
		if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new ApiException("Username is already exists!.");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new ApiException("Email is already exists!.");
        }
        
        
        String role = "CUSTOMER";
        Role userRole = roleRepository.findByName(role).get();
        
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(userRole);
        user.setActive(true);
        
        Staff customer = new Staff();
        customer.setFirstName(user.getFirstname());
        customer.setLastName(user.getLastname());
        customer.setActive(true);
        
        userRepository.save(user);
        
        customer.setUser(user);
		customerRepository.save(customer);
		
		UserResponse userResponse = new UserResponse();
		userResponse.setUsername(registerRequest.getUsername());
		userResponse.setPassword(registerRequest.getPassword());
		
		logger.info("Admin successfully created a customer with username: " + userResponse.getUsername());
		return userResponse;
	}

	@Override
	public PagedResponse<CustomerResponse> getAllCustomers(int page, int size, String sortBy, String direction) {
		
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) 
                ? Sort.by(sortBy).descending() 
                : Sort.by(sortBy).ascending();
		PageRequest pageable = PageRequest.of(page, size, sort);
		Page<Staff> page1 = customerRepository.findAll(pageable);
		List<Staff> customers = page1.getContent();
		List<CustomerResponse> customersResponse = new ArrayList<>();
		for(Staff customer:customers) {
			CustomerResponse customerResponse = dtoConversion.convertCustomerToResponse(customer);
			customersResponse.add(customerResponse);
		}
		
		logger.info("Admin successfully getting all customers");
		return new PagedResponse<>(customersResponse,page1.getNumber(), page1.getSize(), page1.getTotalElements(), page1.getTotalPages(), page1.isLast());
	}

	@Override
	public CustomerResponse getCustomerById(int id) {
		Optional<Staff> oCustomer = customerRepository.findById(id);
		if(oCustomer.isEmpty()) {
			throw new ResourceNotFoundException("Customer not available with id: "+ id);
		}
		Staff customer = oCustomer.get();
		CustomerResponse customerResponse = dtoConversion.convertCustomerToResponse(customer);
		logger.info("Admin successfully getting customer with id:" + id);
		return customerResponse;
	}

	@Override
	public void reactivateCustomer(int id){
		Optional<Staff> oCustomer = customerRepository.findById(id);
		if(oCustomer.isEmpty()) {
			throw new ResourceNotFoundException("Customer not available with id: "+ id);
		}
		Staff customer = oCustomer.get();
		if(customer.isActive() && customer.getUser().isActive()) {
			throw new AlreadyReportedException("Customer is already active");
		}
		customer.setActive(true);
		customer.getUser().setActive(true);
		customerRepository.save(customer);
		
		logger.info("Admin succesfully reactivated the customer with id: " + id);
	}

	
	@Override
	public void deleteCustomer(int id){
		Optional<Staff> oCustomer = customerRepository.findById(id);
		if(oCustomer.isEmpty()) {
			throw new ResourceNotFoundException("Customer not available with id: "+ id);
		}
		Staff customer = oCustomer.get();
		if(!customer.isActive()) {
			throw new AlreadyReportedException("Customer is already deleted");
		}
		customer.setActive(false);
		customer.getUser().setActive(false);
		customerRepository.save(customer);
		logger.info("Admin succesfully deleted the customer with id: " + id);
	}

	@Override
	public PagedResponse<ContactResponse> getAllContacts(String customerId, int page, int size, String sortBy,
			String direction) {
		
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) 
                ? Sort.by(sortBy).descending() 
                : Sort.by(sortBy).ascending();
		PageRequest pageable = PageRequest.of(page, size, sort);
		
		User user = userRepository.findByUsernameOrEmail(customerId, customerId);
		Staff customer = customerRepository.findByUser(user);
		
		
		Page<Contact> page1 = contactRepository.findByStaff(customer, pageable);
		
		List<Contact> contacts = page1.getContent();
		
		List<ContactResponse> contactResponses = new ArrayList<>();
		for(Contact contact:contacts) {
			ContactResponse contactResponse = dtoConversion.convertContactToResponse(contact);
			contactResponses.add(contactResponse);
		}
		
		logger.info("Customer " + customerId + " successfully getting all contacts");
		return new PagedResponse<>(contactResponses,page1.getNumber(), page1.getSize(), page1.getTotalElements(), page1.getTotalPages(), page1.isLast());
	}

	
	
	@Override
	public ContactResponse createAContact(String customerId, ContactRequest contactRequest) {
		
		User user = userRepository.findByUsernameOrEmail(customerId, customerId);
		Staff customer = customerRepository.findByUser(user);
		
		Contact contact = dtoConversion.convertRequestToContact(contactRequest);
		
		contact.setStaff(customer);
		
		contactRepository.save(contact);
		ContactResponse contactResponse = dtoConversion.convertContactToResponse(contact);
		logger.info("Customer " + customerId + " successfully created a new contact");
		return contactResponse;
	}

	@Override
	public ContactResponse getContactById(String customerId, int id) {
		User user = userRepository.findByUsernameOrEmail(customerId, customerId);
		Staff customer = customerRepository.findByUser(user);
		List<Contact> contacts = customer.getContacts();
		for(Contact contact:contacts) {
			if(contact.getId() == id) {
				ContactResponse contactResponse = dtoConversion.convertContactToResponse(contact);
				logger.info("Customer " + customerId + " successfully get contact with id: " + id);
				return contactResponse;
			}
		}
		throw new ResourceNotFoundException("Contact with contact id: " + id + " is not available");
		
	}

	@Override
	public void reactivateContact(String customerId, int id) {
		User user = userRepository.findByUsernameOrEmail(customerId, customerId);
		Staff customer = customerRepository.findByUser(user);
		List<Contact> contacts = customer.getContacts();
		for(Contact contact:contacts) {
			if(contact.getId() == id) {
				if(contact.isActive()) {
					throw new AlreadyReportedException("Contact is already active");
				}
				contact.setActive(true);
				contactRepository.save(contact);
				logger.info("Customer " + customerId + " successfully active contact with id: " + id);
				return;
				
			}
		}
		throw new ResourceNotFoundException("Contact with contact id: " + id + " is not available");
		
	}
	

	@Override
	public void deleteContact(String customerId, int id) {
		User user = userRepository.findByUsernameOrEmail(customerId, customerId);
		Staff customer = customerRepository.findByUser(user);
		List<Contact> contacts = customer.getContacts();
		for(Contact contact:contacts) {
			if(contact.getId() == id) {
				if(contact.isActive() == false) {
					throw new AlreadyReportedException("Contact is already deactivated");
				}
				contact.setActive(false);
				contactRepository.save(contact);
				logger.info("Customer " + customerId + " successfully deactivated contact with id: " + id);
				return;
			}
			
		}
		throw new ResourceNotFoundException("Contact with contact id: " + id + " is not available");
	}

	
	@Override
	public ContactDetailResponse createAContactDetail(String customerId, ContactDetailRequest contactDetailRequest) {
		User user = userRepository.findByUsernameOrEmail(customerId, customerId);
		Staff customer = customerRepository.findByUser(user);
		List<Contact> contacts = customer.getContacts();
		int temp = 0;
		for(Contact contact:contacts) {
			if(contact.getId() == contactDetailRequest.getContact_id() && contact.isActive()) {
				temp = 1;
			}
		}
		if(temp == 0) {
			throw new ResourceNotFoundException("Contact with contact id: " + contactDetailRequest.getContact_id()  + " is not available");
		}
		ContactDetail contactDetail = dtoConversion.convertRequestToContactDetail(contactDetailRequest);
		Contact contact = contactRepository.findById(contactDetailRequest.getContact_id()).get();
		
		contactDetail.setContact(contact);
		contactDetailRepository.save(contactDetail);
		logger.info("Customer " + customerId + " successfully created a new contact detail for contact with id: " + contactDetailRequest.getContact_id() );
		return dtoConversion.convertContactDetailToResponse(contactDetail);
		
		
	}

	@Override
	public ContactDetailResponse getContactDetailById(String customerId, int id) {
		User user = userRepository.findByUsernameOrEmail(customerId, customerId);
		Staff customer = customerRepository.findByUser(user);
		List<Contact> contacts = customer.getContacts();
		for(Contact contact:contacts) {
			Optional<ContactDetail> contactDetail = contactDetailRepository.findByContact(contact);
			if(contactDetail.isPresent()) {
				logger.info("Customer " + customerId + " successfully getting contact detail with id: " + id);
				return dtoConversion.convertContactDetailToResponse(contactDetail.get());
			}
		}
		throw new ResourceNotFoundException("Contact Detail not found");
	}
	
	

}
