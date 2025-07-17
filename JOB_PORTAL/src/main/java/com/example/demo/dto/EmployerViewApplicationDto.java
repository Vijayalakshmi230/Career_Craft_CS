package com.example.demo.dto;


import lombok.Data;

@Data
public class EmployerViewApplicationDto {
    private ApplicationDto application;
    private String resumeDownloadUrl;
    private JobDto job;  
}
