package com.techlabs.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull(message = "First name is missing")
    private String firstname;
    
    @NotNull(message = "Last name is missing")
    private String lastname;
    
    
    @Column(nullable = false, unique = true)
    @NotNull(message = "Username is missing")
    private String username;
    
    @Column(nullable = false, unique = true)
    @NotNull(message = "Email is missing")
    private String email;
    
    @Column(nullable = false)
    @NotNull(message = "Password is missing")
    private String password;
    
    private boolean isActive;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	@JsonBackReference
    private Role role;

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
	
    
}
