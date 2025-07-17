package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.*;
import com.example.demo.entity.ApplicationStatus;

public interface ApplicationService {
    ApplicationDto applyToJob(Long jobId, ApplicationDto appDto);

    ApplicationDto updateApplication(Long applicationId, ApplicationDto appDto);

    void deleteApplication(Long applicationId);

    List<JobSeekerApplicationViewDto> getJobSeekerApplications();

    List<EmployerViewApplicationDto> getApplicationsForJob(Long jobId);

    List<EmployerViewApplicationDto> getAllApplicationsForMyJobs();

    ApplicationDto updateApplicationStatus(Long applicationId, ApplicationStatus status);
}
