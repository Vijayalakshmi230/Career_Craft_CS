package com.example.demo.serviceImpl;

import com.example.demo.dto.UserProfileDto;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepo userRepo;

    @Value("${file.upload-dir:uploads}") // default 'uploads' if not set
    private String uploadDir;

    @Override
    public UserProfileDto getProfile(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDto(user);
    }

    @Override
    public UserProfileDto updateProfile(String email, UserProfileDto profileDto) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields only if provided (not null & not empty)
        if (profileDto.getName() != null && !profileDto.getName().isEmpty()) {
            user.setUsername(profileDto.getName());
        }

        if (profileDto.getProfileDescription() != null && !profileDto.getProfileDescription().isEmpty()) {
            user.setProfileDescirption(profileDto.getProfileDescription());
        }

        // You can add more fields similarly if needed

        userRepo.save(user);
        return toDto(user);
    }

    private UserProfileDto toDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setName(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setProfileDescription(user.getProfileDescirption());
        return dto;
    }
}
