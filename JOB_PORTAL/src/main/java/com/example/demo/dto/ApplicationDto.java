package com.example.demo.dto;

import com.example.demo.entity.ApplicationStatus;

import lombok.Data;

@Data
public class ApplicationDto {
	private Long id;
	private Long userId;
	private String name;
	private Integer age;
	private String email;
	private String college;
	private Double cgpa;
	private String previousCompany;
	private Integer yearsOfExperience;
	private String previousRole;
	private ApplicationStatus status;
}
