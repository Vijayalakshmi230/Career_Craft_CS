import { Component, OnInit } from '@angular/core';
import { JobDto } from '../../service-jobs/job-model.model';
import { JobsService } from '../../service-jobs/jobs';
import { Application } from '../../service-application/application';
import { ApplicationDto } from '../../service-application/application-model';
import { ResumeService } from '../../services-resume/resume';

@Component({
  selector: 'app-view-all-jobs',
  standalone: false,
  templateUrl: './view-all-jobs.html',
  styleUrl: './view-all-jobs.css'
})
export class ViewAllJobs implements OnInit {
  allJobs: JobDto[] = [];
  filteredJobs: JobDto[] = [];
  searchTerm: string = '';

  selectedJob: JobDto | null = null;
  applicationForm: ApplicationDto = {
    userId: 0,
    name: '',
    age: 0,
    email: '',
    college: '',
    cgpa: 0,
    previousCompany: '',
    yearsOfExperience: 0,
    previousRole: '',
    status: 'Applied'
  };
  selectedFile: File | null = null;

  constructor(
    private jobService: JobsService,
    private applicationService: Application,
    private resumeService: ResumeService
  ) { }

  ngOnInit(): void {
    this.loadAllJobs();
  }

  loadAllJobs(): void {
    this.jobService.getAllJobs().subscribe({
      next: (jobs) => {
        this.allJobs = jobs;
        this.filteredJobs = jobs;
        console.log('Loaded jobs:', jobs);
      },
      error: (error) => {
        console.error('Error fetching jobs:', error);
      }
    });
  }

  applyFilter(): void {
    const term = this.searchTerm.trim().toLowerCase();
    this.filteredJobs = !term
      ? this.allJobs
      : this.allJobs.filter(job =>
        job.company?.toLowerCase().includes(term) ||
        job.designation?.toLowerCase().includes(term)
      );
  }

  openApplyModal(job: JobDto): void {
    this.selectedJob = job;
    const storedUserId = localStorage.getItem('userId');
    this.applicationForm.userId = storedUserId ? parseInt(storedUserId, 10) : 0;
  }

  closeApplyModal(): void {
    this.selectedJob = null;
    this.applicationForm = {
      userId: this.applicationForm.userId,
      name: '',
      age: 0,
      email: '',
      college: '',
      cgpa: 0,
      previousCompany: '',
      yearsOfExperience: 0,
      previousRole: '',
      status: 'Applied'
    };
    this.selectedFile = null;
  }

  onFileSelected(event: any): void {
    if (event.target.files && event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
    }
  }

  submitApplication(): void {
    if (!this.selectedJob) return;

    console.log('Submitting application:', this.applicationForm);
    this.applicationService.applyToJob(this.selectedJob.id!, this.applicationForm).subscribe({
      next: (response) => {
        console.log('Application submitted:', response);
        alert('Application submitted successfully!');
        this.closeApplyModal();
      },
      error: (err) => {
        if (err.status === 400 && err.error && err.error.message) {
          alert(err.error.message);
        } else {
          alert('Something went wrong. Please try again.');
        }
      }

    });
  }

}
