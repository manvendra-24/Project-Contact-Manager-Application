package com.techlabs.repository;

import com.techlabs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	User findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail2);

	User findByUsername(String username);

	
}
