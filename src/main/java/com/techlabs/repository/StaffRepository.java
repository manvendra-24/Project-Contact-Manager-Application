package com.techlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.entity.Staff;
import com.techlabs.entity.User;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer>{

	Staff findByUser(User user);
	

}
