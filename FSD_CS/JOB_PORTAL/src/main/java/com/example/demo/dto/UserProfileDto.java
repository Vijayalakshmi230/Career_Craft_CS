package com.example.demo.dto;

import com.example.demo.entity.Role;

import lombok.Data;

@Data
public class UserProfileDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String profileDescription;
}