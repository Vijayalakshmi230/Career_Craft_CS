package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.RegisterRequest;

public interface AuthService {
    MessageResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    MessageResponse logout(String email);
    MessageResponse deleteAccount(String email);
    MessageResponse changePassword(String email, String oldPassword, String newPassword);

}
