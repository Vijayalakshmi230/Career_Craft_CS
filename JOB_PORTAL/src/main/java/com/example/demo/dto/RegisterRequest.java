package com.example.demo.dto;

import com.example.demo.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
	
	private String name;
    private String email;
    private String password;
    private Role role; 

}
