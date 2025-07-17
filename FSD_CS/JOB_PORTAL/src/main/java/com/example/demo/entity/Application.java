package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id")
    private User jobSeeker;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private String name;
    private Integer age;
    private String email;
    private String college;
    private Double cgpa;
    private String previousCompany;
    private Integer yearsOfExperience;
    private String previousRole;
}
