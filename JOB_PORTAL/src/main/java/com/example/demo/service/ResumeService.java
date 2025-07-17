package com.example.demo.service;


import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Resume;

public interface ResumeService {
    void uploadResume(MultipartFile file, Long userId);
    Resume getResumeByUserId(Long userId);
    void deleteResume(Long userId);



}
