package com.example.demo.service;

import com.example.demo.dto.UserProfileDto;

public interface ProfileService {
    UserProfileDto getProfile(String email);
    UserProfileDto updateProfile(String email, UserProfileDto profileDto);
}
