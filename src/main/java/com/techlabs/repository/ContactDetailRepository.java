package com.techlabs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.entity.Contact;
import com.techlabs.entity.ContactDetail;

@Repository
public interface ContactDetailRepository extends JpaRepository<ContactDetail, Integer>{

	Optional<ContactDetail> findByContact(Contact contact);

}

