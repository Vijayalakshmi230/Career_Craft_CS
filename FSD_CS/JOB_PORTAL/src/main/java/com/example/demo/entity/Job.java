// Job.java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String designation;
    private String company;
    private String salary;     
    private String location;     

    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User postedBy;
}
