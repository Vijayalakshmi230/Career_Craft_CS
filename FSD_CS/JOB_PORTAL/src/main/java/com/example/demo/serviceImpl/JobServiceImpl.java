// JobServiceImpl.java
package com.example.demo.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.JobDto;
import com.example.demo.entity.Job;
import com.example.demo.entity.User;
import com.example.demo.entity.Role;
import com.example.demo.repo.JobRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.JobService;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobRepo jobRepo;

	@Autowired
	private UserRepo userRepo;

	@Override
	public JobDto createJob(JobDto jobDto, String employerEmail) {
		User employer = userRepo.findByEmail(employerEmail)
				.orElseThrow(() -> new RuntimeException("Employer not found"));
		if (employer.getRole() != Role.Employer) {
			throw new RuntimeException("Unauthorized: Only employers can post jobs");
		}

		Job job = new Job();
		job.setTitle(jobDto.getTitle());
		job.setDescription(jobDto.getDescription());
		job.setDesignation(jobDto.getDesignation());
		job.setCompany(jobDto.getCompany());
		job.setSalary(jobDto.getSalary());
		job.setLocation(jobDto.getLocation());
		job.setPostedBy(employer);
		job = jobRepo.save(job);

		return toDto(job);
	}

	@Override
	public JobDto updateJob(Long jobId, JobDto jobDto, String employerEmail) {
		User employer = userRepo.findByEmail(employerEmail)
				.orElseThrow(() -> new RuntimeException("Employer not found"));

		Job job = jobRepo.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

		if (!job.getPostedBy().getId().equals(employer.getId())) {
			throw new RuntimeException("Unauthorized: cannot update others' jobs");
		}

		job.setTitle(jobDto.getTitle());
		job.setDescription(jobDto.getDescription());
		job.setDesignation(jobDto.getDesignation());
		job.setCompany(jobDto.getCompany());
		job.setSalary(jobDto.getSalary());
		job.setLocation(jobDto.getLocation());

		job = jobRepo.save(job);

		return toDto(job);
	}

	@Override
	public void deleteJob(Long jobId, String employerEmail) {
		User employer = userRepo.findByEmail(employerEmail)
				.orElseThrow(() -> new RuntimeException("Employer not found"));

		Job job = jobRepo.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

		if (!job.getPostedBy().getId().equals(employer.getId())) {
			throw new RuntimeException("Unauthorized: cannot delete others' jobs");
		}

		jobRepo.delete(job);
	}

	@Override
	public JobDto getJobById(Long jobId) {
		Job job = jobRepo.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));
		return toDto(job);
	}

	@Override
	public List<JobDto> getAllJobs() {
		return jobRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public List<JobDto> searchJobsByDesignation(String designation) {
		return jobRepo.findByDesignationContainingIgnoreCase(designation).stream().map(this::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<JobDto> searchJobsByCompany(String company) {
		return jobRepo.findByCompanyContainingIgnoreCase(company).stream().map(this::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<JobDto> getEmployerJobs(String employerEmail) {
		User employer = userRepo.findByEmail(employerEmail)
				.orElseThrow(() -> new RuntimeException("Employer not found"));

		if (employer.getRole() != Role.Employer) {
			throw new RuntimeException("Unauthorized: Only employers can view their jobs");
		}

		return jobRepo.findByPostedBy(employer).stream().map(this::toDto).collect(Collectors.toList());
	}

	private JobDto toDto(Job job) {
	    JobDto dto = new JobDto();
	    dto.setId(job.getId());
	    dto.setTitle(job.getTitle());
	    dto.setDescription(job.getDescription());
	    dto.setDesignation(job.getDesignation());
	    dto.setCompany(job.getCompany());
	    dto.setSalary(job.getSalary());
	    dto.setLocation(job.getLocation());
	    dto.setPostedByName(job.getPostedBy() != null ? job.getPostedBy().getUsername() : "Unknown");
	    return dto;
	}


}
