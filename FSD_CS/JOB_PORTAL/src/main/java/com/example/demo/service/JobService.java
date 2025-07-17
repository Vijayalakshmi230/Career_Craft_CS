package com.example.demo.service;

import java.util.List;
import com.example.demo.dto.JobDto;

public interface JobService {
    JobDto createJob(JobDto jobDto, String employerEmail);
    JobDto updateJob(Long jobId, JobDto jobDto, String employerEmail);
    void deleteJob(Long jobId, String employerEmail);
    JobDto getJobById(Long jobId);
    List<JobDto> getAllJobs();
    List<JobDto> searchJobsByDesignation(String designation);
    List<JobDto> searchJobsByCompany(String company);
    List<JobDto> getEmployerJobs(String employerEmail);
}
