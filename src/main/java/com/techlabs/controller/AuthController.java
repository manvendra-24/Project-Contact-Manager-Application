package com.techlabs.controller;

import com.techlabs.dto.AuthResponse;
import com.techlabs.dto.LoginRequest;
import com.techlabs.dto.RegisterRequest;
import com.techlabs.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/auth/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginDto){
        String token = authService.login(loginDto);
        
        AuthResponse jwtAuthResponse = new AuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = "/auth/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerDto, @RequestParam(name="role") String role){
        String response = authService.register(registerDto, role);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
