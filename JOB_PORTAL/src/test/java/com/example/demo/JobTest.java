package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.JobDto;
import com.example.demo.entity.Job;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repo.JobRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.serviceImpl.JobServiceImpl;

@ExtendWith(MockitoExtension.class)
class JobTest {

    @Mock
    private JobRepo jobRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private JobServiceImpl jobService;

    @Test
    void createJob_ShouldSuccess_WhenEmployerAuthorized() {
        // Arrange
        JobDto jobDto = new JobDto();
        jobDto.setTitle("Software Engineer");
        jobDto.setDescription("Develop software");
        jobDto.setCompany("Tech Corp");
        
        User employer = new User();
        employer.setEmail("employer@example.com");
        employer.setRole(Role.Employer);

        when(userRepo.findByEmail("employer@example.com")).thenReturn(Optional.of(employer));
        when(jobRepo.save(any(Job.class))).thenAnswer(invocation -> {
            Job job = invocation.getArgument(0);
            job.setId(1L);
            return job;
        });

        // Act
        JobDto result = jobService.createJob(jobDto, "employer@example.com");

        // Assert
        assertNotNull(result);
        assertEquals("Software Engineer", result.getTitle());
        assertEquals("Tech Corp", result.getCompany());
        verify(jobRepo, times(1)).save(any(Job.class));
    }

    @Test
    void createJob_ShouldThrowException_WhenNotEmployer() {
        // Arrange
        JobDto jobDto = new JobDto();
        User jobSeeker = new User();
        jobSeeker.setEmail("jobseeker@example.com");
        jobSeeker.setRole(Role.Job_Seeker);

        when(userRepo.findByEmail("jobseeker@example.com")).thenReturn(Optional.of(jobSeeker));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            jobService.createJob(jobDto, "jobseeker@example.com"));
    }

    @Test
    void updateJob_ShouldSuccess_WhenJobOwner() {
        // Arrange
        Long jobId = 1L;
        JobDto jobDto = new JobDto();
        jobDto.setTitle("Updated Title");
        
        User employer = new User();
        employer.setEmail("employer@example.com");
        employer.setId(1L);
        
        Job existingJob = new Job();
        existingJob.setPostedBy(employer);

        when(userRepo.findByEmail("employer@example.com")).thenReturn(Optional.of(employer));
        when(jobRepo.findById(jobId)).thenReturn(Optional.of(existingJob));
        when(jobRepo.save(any(Job.class))).thenReturn(existingJob);

        // Act
        JobDto result = jobService.updateJob(jobId, jobDto, "employer@example.com");

        // Assert
        assertEquals("Updated Title", result.getTitle());
        verify(jobRepo, times(1)).save(any(Job.class));
    }

    @Test
    void updateJob_ShouldThrowException_WhenNotJobOwner() {
        // Arrange
        Long jobId = 1L;
        JobDto jobDto = new JobDto();
        
        User employer1 = new User();
        employer1.setEmail("employer1@example.com");
        employer1.setId(1L);
        
        User employer2 = new User();
        employer2.setEmail("employer2@example.com");
        employer2.setId(2L);
        
        Job existingJob = new Job();
        existingJob.setPostedBy(employer1);

        when(userRepo.findByEmail("employer2@example.com")).thenReturn(Optional.of(employer2));
        when(jobRepo.findById(jobId)).thenReturn(Optional.of(existingJob));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            jobService.updateJob(jobId, jobDto, "employer2@example.com"));
    }

    @Test
    void deleteJob_ShouldSuccess_WhenJobOwner() {
        // Arrange
        Long jobId = 1L;
        User employer = new User();
        employer.setEmail("employer@example.com");
        employer.setId(1L);
        
        Job job = new Job();
        job.setPostedBy(employer);

        when(userRepo.findByEmail("employer@example.com")).thenReturn(Optional.of(employer));
        when(jobRepo.findById(jobId)).thenReturn(Optional.of(job));

        // Act
        assertDoesNotThrow(() -> jobService.deleteJob(jobId, "employer@example.com"));

        // Assert
        verify(jobRepo, times(1)).delete(job);
    }

    @Test
    void getAllJobs_ShouldReturnAllJobs() {
        // Arrange
        Job job1 = new Job();
        job1.setTitle("Job 1");
        Job job2 = new Job();
        job2.setTitle("Job 2");

        when(jobRepo.findAll()).thenReturn(Arrays.asList(job1, job2));

        // Act
        List<JobDto> result = jobService.getAllJobs();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Job 1", result.get(0).getTitle());
        assertEquals("Job 2", result.get(1).getTitle());
    }

    @Test
    void getJobById_ShouldReturnJob_WhenExists() {
        // Arrange
        Long jobId = 1L;
        Job job = new Job();
        job.setTitle("Software Engineer");

        when(jobRepo.findById(jobId)).thenReturn(Optional.of(job));

        // Act
        JobDto result = jobService.getJobById(jobId);

        // Assert
        assertEquals("Software Engineer", result.getTitle());
    }

    @Test
    void getJobById_ShouldThrowException_WhenNotExists() {
        // Arrange
        Long jobId = 1L;
        when(jobRepo.findById(jobId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> jobService.getJobById(jobId));
    }

    @Test
    void searchJobsByDesignation_ShouldReturnFilteredResults() {
        // Arrange
        Job job1 = new Job();
        job1.setDesignation("Developer");
        Job job2 = new Job();
        job2.setDesignation("Manager");

        when(jobRepo.findByDesignationContainingIgnoreCase("dev")).thenReturn(Arrays.asList(job1));

        // Act
        List<JobDto> result = jobService.searchJobsByDesignation("dev");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Developer", result.get(0).getDesignation());
    }

    @Test
    void getEmployerJobs_ShouldReturnOnlyOwnersJobs() {
        // Arrange
        User employer = new User();
        employer.setEmail("employer@example.com");
        employer.setRole(Role.Employer);  // THIS IS THE CRUCIAL LINE THAT WAS MISSING
        employer.setId(1L);
        
        Job job1 = new Job();
        job1.setPostedBy(employer);
        job1.setTitle("Job 1");
        
        Job job2 = new Job();
        job2.setPostedBy(employer);
        job2.setTitle("Job 2");

        // Mock the repository calls
        when(userRepo.findByEmail("employer@example.com")).thenReturn(Optional.of(employer));
        when(jobRepo.findByPostedBy(employer)).thenReturn(Arrays.asList(job1, job2));

        // Act
        List<JobDto> result = jobService.getEmployerJobs("employer@example.com");

        // Assert
        assertEquals(2, result.size());
        assertEquals("Job 1", result.get(0).getTitle());
        assertEquals("Job 2", result.get(1).getTitle());
        
        // Verify the repository interactions
        verify(userRepo, times(1)).findByEmail("employer@example.com");
        verify(jobRepo, times(1)).findByPostedBy(employer);
    }
}