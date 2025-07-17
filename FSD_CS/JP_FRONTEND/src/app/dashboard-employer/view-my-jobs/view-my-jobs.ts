import { Component, OnInit } from '@angular/core';
import { JobsService } from '../../service-jobs/jobs';
import { JobDto } from '../../service-jobs/job-model.model';

declare global {
  interface Window { bootstrap: any; }
}

@Component({
  selector: 'app-view-my-jobs',
  standalone: false,
  templateUrl: './view-my-jobs.html',
  styleUrl: './view-my-jobs.css'
})
export class ViewMyJobs implements OnInit {
  myJobs: JobDto[] = [];       
  selectedJob: JobDto = {};         

  constructor(private jobsService: JobsService) {}

  ngOnInit(): void {
    this.loadMyJobs();
  }

  loadMyJobs(): void {
    this.jobsService.getMyJobs().subscribe({
      next: (jobs) => this.myJobs = jobs,
      error: (err) => console.error('Error fetching jobs:', err)
    });
  }

  openEditModal(job: JobDto): void {
    
    this.selectedJob = { ...job };
  
    const modal: any = new (window as any).bootstrap.Modal(document.getElementById('editModal'));
    modal.show();
  }

  saveJob(): void {
    if (this.selectedJob.id != null) {
      this.jobsService.updateJob(this.selectedJob.id, this.selectedJob).subscribe({
        next: () => this.loadMyJobs(),
        error: (err) => console.error('Error updating job:', err)
      });
    }
   
    const modal: any = window.bootstrap.Modal.getInstance(document.getElementById('editModal'));
    modal.hide();
  }

  deleteJob(jobId?: number): void {
    if (jobId != null) {
      this.jobsService.deleteJob(jobId).subscribe({
        next: () => this.loadMyJobs(),
        error: (err) => console.error('Error deleting job:', err)
      });
    }
  }
}