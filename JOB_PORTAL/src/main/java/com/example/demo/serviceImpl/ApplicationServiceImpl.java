package com.example.demo.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.exception.AlreadyAppliedException;
import com.example.demo.repo.*;
import com.example.demo.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepo applicationRepo;
    @Autowired
    private JobRepo jobRepo;
    @Autowired
    private UserRepo userRepo;
    
    private String getLoggedInUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ApplicationDto applyToJob(Long jobId, ApplicationDto appDto) {
        String jobSeekerEmail = getLoggedInUserEmail();

        User seeker = userRepo.findByEmail(jobSeekerEmail)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));
        if (seeker.getRole() != Role.Job_Seeker)
            throw new RuntimeException("Only job seekers can apply");

        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (applicationRepo.findByJobAndJobSeeker(job, seeker).isPresent())
            throw new AlreadyAppliedException("You have already applied to this job");


        Application app = new Application();
        app.setJob(job);
        app.setJobSeeker(seeker);
        app.setStatus(ApplicationStatus.Applied);

        copyDtoToEntity(appDto, app);

        app = applicationRepo.save(app);
        return toDto(app);
    }

    @Override
    public ApplicationDto updateApplication(Long applicationId, ApplicationDto appDto) {
        String jobSeekerEmail = getLoggedInUserEmail();

        User seeker = userRepo.findByEmail(jobSeekerEmail)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));

        Application app = applicationRepo.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!app.getJobSeeker().getId().equals(seeker.getId()))
            throw new RuntimeException("Unauthorized");

        copyDtoToEntity(appDto, app);
        app = applicationRepo.save(app);
        return toDto(app);
    }

    @Override
    public void deleteApplication(Long applicationId) {
        String jobSeekerEmail = getLoggedInUserEmail();

        User seeker = userRepo.findByEmail(jobSeekerEmail)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));

        Application app = applicationRepo.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!app.getJobSeeker().getId().equals(seeker.getId()))
            throw new RuntimeException("Unauthorized");

        applicationRepo.delete(app);
    }

    @Override
    public List<JobSeekerApplicationViewDto> getJobSeekerApplications() {
        String jobSeekerEmail = getLoggedInUserEmail();

        User seeker = userRepo.findByEmail(jobSeekerEmail)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));

        return applicationRepo.findByJobSeeker(seeker).stream()
                .map(app -> {
                    JobSeekerApplicationViewDto dto = new JobSeekerApplicationViewDto();
                    dto.setApplication(toDto(app));
                    dto.setJob(toJobDto(app.getJob()));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<EmployerViewApplicationDto> getApplicationsForJob(Long jobId) {
        String employerEmail = getLoggedInUserEmail();

        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getPostedBy().getEmail().equals(employerEmail))
            throw new RuntimeException("Unauthorized to view applications for this job");

        return applicationRepo.findByJob(job).stream()
                .map(app -> {
                    EmployerViewApplicationDto dto = new EmployerViewApplicationDto();
                    dto.setApplication(toDto(app));
                    dto.setJob(toJobDto(job));
                    dto.setResumeDownloadUrl("/api/resumes/download/" + app.getJobSeeker().getId());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public ApplicationDto updateApplicationStatus(Long applicationId, ApplicationStatus status) {
        String employerEmail = getLoggedInUserEmail();

        Application app = applicationRepo.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!app.getJob().getPostedBy().getEmail().equals(employerEmail))
            throw new RuntimeException("Unauthorized to update application status");

        app.setStatus(status);
        app = applicationRepo.save(app);
        return toDto(app);
    }

    @Override
    public List<EmployerViewApplicationDto> getAllApplicationsForMyJobs() {
        String employerEmail = getLoggedInUserEmail();

        User employer = userRepo.findByEmail(employerEmail)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        List<Job> jobs = jobRepo.findByPostedBy(employer);
        if (jobs.isEmpty()) return List.of();

        List<Application> apps = applicationRepo.findByJobIn(jobs);
        return apps.stream().map(app -> {
            EmployerViewApplicationDto dto = new EmployerViewApplicationDto();
            dto.setApplication(toDto(app));
            dto.setJob(toJobDto(app.getJob()));
            dto.setResumeDownloadUrl("/api/resumes/download/" + app.getJobSeeker().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    private ApplicationDto toDto(Application app) {
        ApplicationDto dto = new ApplicationDto();
        dto.setId(app.getId());
        dto.setName(app.getName());
        dto.setAge(app.getAge());
        dto.setEmail(app.getEmail());
        dto.setCollege(app.getCollege());
        dto.setCgpa(app.getCgpa());
        dto.setPreviousCompany(app.getPreviousCompany());
        dto.setYearsOfExperience(app.getYearsOfExperience());
        dto.setPreviousRole(app.getPreviousRole());
        dto.setStatus(app.getStatus());
        dto.setUserId(app.getJobSeeker().getId());
        return dto;
    }

    private JobDto toJobDto(Job job) {
        JobDto dto = new JobDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setDesignation(job.getDesignation());
        dto.setLocation(job.getLocation());
        dto.setSalary(job.getSalary());
        dto.setCompany(job.getCompany());
        if (job.getPostedBy() != null) {
            dto.setPostedByName(job.getPostedBy().getUsername());
        }
        return dto;
    }

    private void copyDtoToEntity(ApplicationDto dto, Application app) {
        app.setName(dto.getName());
        app.setAge(dto.getAge());
        app.setEmail(dto.getEmail());
        app.setCollege(dto.getCollege());
        app.setCgpa(dto.getCgpa());
        app.setPreviousCompany(dto.getPreviousCompany());
        app.setYearsOfExperience(dto.getYearsOfExperience());
        app.setPreviousRole(dto.getPreviousRole());
    }
}
