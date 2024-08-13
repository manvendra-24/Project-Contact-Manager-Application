package com.techlabs.service;

import com.techlabs.entity.User;
import com.techlabs.exception.ResourceNotFoundException;
import com.techlabs.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    	User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if(user == null) {
        	throw new ResourceNotFoundException("Username or Email not found");
        }
        
    	GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
    	Set<GrantedAuthority> authorities = Collections.singleton(authority);

        
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), authorities);
    }
}
