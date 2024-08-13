package com.techlabs.service;

import com.techlabs.entity.Admin;
import com.techlabs.entity.Role;
import com.techlabs.entity.User;
import com.techlabs.exception.ApiException;
import com.techlabs.exception.ResourceNotFoundException;
import com.techlabs.dto.LoginRequest;
import com.techlabs.dto.RegisterRequest;
import com.techlabs.repository.AdminRepository;
import com.techlabs.repository.RoleRepository;
import com.techlabs.repository.UserRepository;
import com.techlabs.security.JwtTokenProvider;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AdminRepository adminRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           AdminRepository adminRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //Admin and Staff login
    @Override
    public String login(LoginRequest loginDto) {
    	
    	String username = loginDto.getUsername();
    	String password = loginDto.getPassword();
    	
    	User user = userRepository.findByUsername(username);
    	if(user.isActive()) {
    		UsernamePasswordAuthenticationToken temp = new UsernamePasswordAuthenticationToken(username, password);
    		Authentication authentication = authenticationManager.authenticate(temp);
    		SecurityContextHolder.getContext().setAuthentication(authentication);
    		String token = jwtTokenProvider.generateToken(authentication);
    		return token;
    	}
    	throw new ResourceNotFoundException( "User not exists");
    	
    }

    
    //Admin register
    @Override
    public String register(RegisterRequest registerDto, String role) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new ApiException("Username is already exists!.");
        }
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new ApiException("Email is already exists!.");
        }
        
        
        Optional<Role> oUserRole = roleRepository.findByName(role);
        if(oUserRole.isEmpty()) {
        	throw new ResourceNotFoundException("Role does not exists");
        }
        Role userRole = oUserRole.get();
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(userRole);
        Admin admin = new Admin();
        if(userRole.getName().equalsIgnoreCase("admin")) {
        	admin.setFirstName(user.getFirstname());
        	admin.setLastName(user.getLastname());
        	admin.setActive(true);
        }
        user.setActive(true);
        userRepository.save(user);
        admin.setUser(user);
        adminRepository.save(admin);
        return "Admin registered successfully!.";
    }
}
