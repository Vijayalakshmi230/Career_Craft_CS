package com.example.demo.dto;

import lombok.Data;

@Data
public class JobDto {
    private Long id;
    private String title;
    private String description;
    private String designation;
    private String company;
    private String salary;       
    private String location;    
    private String postedByName;
}
