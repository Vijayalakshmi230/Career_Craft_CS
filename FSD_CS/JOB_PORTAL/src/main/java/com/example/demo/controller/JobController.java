// JobController.java
package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.JobDto;
import com.example.demo.service.JobService;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:4200")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<JobDto> addJob(@RequestBody JobDto jobDto, Principal principal) {
        return ResponseEntity.ok(jobService.createJob(jobDto, principal.getName()));
    }

    @PutMapping("/{jobId}")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<JobDto> updateJob(@PathVariable Long jobId,
                                            @RequestBody JobDto jobDto,
                                            Principal principal) {
        return ResponseEntity.ok(jobService.updateJob(jobId, jobDto, principal.getName()));
    }
    
    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<Void> deleteJob(@PathVariable Long jobId, Principal principal) {
        jobService.deleteJob(jobId, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<List<JobDto>> getEmployerJobs(Principal principal) {
        return ResponseEntity.ok(jobService.getEmployerJobs(principal.getName()));
    }

    
    @GetMapping
    public ResponseEntity<List<JobDto>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobDto> getJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobService.getJobById(jobId));
    }


    @GetMapping("/search/designation")
    public ResponseEntity<List<JobDto>> searchByDesignation(@RequestParam String designation) {
        return ResponseEntity.ok(jobService.searchJobsByDesignation(designation));
    }


    @GetMapping("/search/company")
    public ResponseEntity<List<JobDto>> searchByCompany(@RequestParam String company) {
        return ResponseEntity.ok(jobService.searchJobsByCompany(company));
    }
}
