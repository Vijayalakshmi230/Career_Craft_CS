package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.ApplicationStatus;
import com.example.demo.service.ApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:4200")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    
    @PostMapping("/job/{jobId}")
    public ResponseEntity<ApplicationDto> applyToJob(
            @PathVariable Long jobId,
            @RequestBody ApplicationDto appDto) {

        ApplicationDto created = applicationService.applyToJob(jobId, appDto);
        return ResponseEntity.ok(created);
    }

 
    @PutMapping("/{applicationId}")
    public ResponseEntity<ApplicationDto> updateApplication(
            @PathVariable Long applicationId,
            @RequestBody ApplicationDto appDto) {

        ApplicationDto updated = applicationService.updateApplication(applicationId, appDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long applicationId) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<JobSeekerApplicationViewDto>> getMyApplications() {
        List<JobSeekerApplicationViewDto> list = applicationService.getJobSeekerApplications();
        return ResponseEntity.ok(list);
    }

 
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<EmployerViewApplicationDto>> getApplicationsForJob(@PathVariable Long jobId) {
        List<EmployerViewApplicationDto> list = applicationService.getApplicationsForJob(jobId);
        return ResponseEntity.ok(list);
    }

  
    @GetMapping("/employer/my")
    public ResponseEntity<List<EmployerViewApplicationDto>> getAllApplicationsForMyJobs() {
        List<EmployerViewApplicationDto> list = applicationService.getAllApplicationsForMyJobs();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationDto> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam ApplicationStatus status) {

        ApplicationDto updated = applicationService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok(updated);
    }
}
