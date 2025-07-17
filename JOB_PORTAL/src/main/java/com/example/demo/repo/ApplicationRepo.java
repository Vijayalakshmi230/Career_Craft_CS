package com.example.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Application;
import com.example.demo.entity.Job;
import com.example.demo.entity.User;

public interface ApplicationRepo extends JpaRepository<Application, Long> {

    List<Application> findByJob(Job job); 
    List<Application> findByJobSeeker(User jobSeeker); 
    Optional<Application> findByJobAndJobSeeker(Job job, User jobSeeker); 
    List<Application> findByJobIn(List<Job> jobs);

}
