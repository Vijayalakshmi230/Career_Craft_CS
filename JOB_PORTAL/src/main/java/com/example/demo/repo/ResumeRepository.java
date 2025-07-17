package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Resume;
import com.example.demo.entity.User;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
      Optional<Resume> findByUser(User user);

}
