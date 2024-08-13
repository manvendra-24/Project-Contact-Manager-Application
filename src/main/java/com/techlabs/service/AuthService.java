package com.techlabs.service;

import com.techlabs.dto.LoginRequest;
import com.techlabs.dto.RegisterRequest;

public interface AuthService {
    String login(LoginRequest loginDto);

    String register(RegisterRequest registerDto, String role);
}
