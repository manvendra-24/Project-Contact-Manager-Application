package com.techlabs.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.entity.Contact;
import com.techlabs.entity.Staff;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>{

	Page<Contact> findByStaff(Staff customer, PageRequest pageable);


}
