package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.serviceImpl.AuthServiceImpl;
import com.example.demo.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class AuthTests {

    @Mock
    private UserRepo userRepo;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void register_ShouldSuccess_WhenEmailIsUnique() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setName("Test User");
        request.setRole(Role.Job_Seeker);
        
        when(userRepo.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        
        MessageResponse response = authService.register(request);
        
        assertEquals("User registered successfully!", response.getMessage());
    }

    @Test
    void register_ShouldThrowException_WhenEmailExists() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("exists@example.com");
        request.setPassword("password");
        request.setName("Test User");
        request.setRole(Role.Job_Seeker);
        
        when(userRepo.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));
        
        assertThrows(RuntimeException.class, () -> authService.register(request));
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsValid() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");
        
        User mockUser = new User();
        mockUser.setEmail(request.getEmail());
        mockUser.setRole(Role.Job_Seeker);
        
        when(userRepo.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUser));
        when(jwtUtil.generateToken(request.getEmail())).thenReturn("mockToken");
        
        LoginResponse response = authService.login(request);
        
        assertEquals("mockToken", response.getToken());
        assertEquals("Job_Seeker", response.getRole());
    }

    @Test
    void changePassword_ShouldSuccess_WhenOldPasswordMatches() {
        String email = "user@example.com";
        User user = new User();
        user.setPassword("encodedOldPass");
        
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPass", "encodedOldPass")).thenReturn(true);
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        
        MessageResponse response = authService.changePassword(email, "oldPass", "newPass");
        
        assertEquals("Password changed successfully", response.getMessage());
    }

    @Test
    void deleteAccount_ShouldSuccess_WhenUserExists() {
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);
        
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        
        MessageResponse response = authService.deleteAccount(email);
        
        assertEquals("Account deleted successfully", response.getMessage());
    }
}