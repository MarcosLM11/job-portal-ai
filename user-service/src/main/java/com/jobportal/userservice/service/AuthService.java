package com.jobportal.userservice.service;

import com.jobportal.userservice.dto.AuthResponse;
import com.jobportal.userservice.dto.LoginRequest;
import com.jobportal.userservice.dto.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
}
