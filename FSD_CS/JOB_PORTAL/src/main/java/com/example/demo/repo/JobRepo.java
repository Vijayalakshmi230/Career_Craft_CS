package com.example.demo.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Job;
import com.example.demo.entity.User;

public interface JobRepo extends JpaRepository<Job, Long> {
    List<Job> findByPostedBy(User employer);
    List<Job> findByDesignationContainingIgnoreCase(String designation);
    List<Job> findByCompanyContainingIgnoreCase(String company);
}
