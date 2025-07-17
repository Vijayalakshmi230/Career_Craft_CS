import { Component } from '@angular/core';
import { JobDto } from '../../service-jobs/job-model.model';
import { JobsService } from '../../service-jobs/jobs';

@Component({
  selector: 'app-post-jobs',
  standalone: false,
  templateUrl: './post-jobs.html',
  styleUrl: './post-jobs.css'
})
export class PostJobs {
  newJob: JobDto = {};

  constructor(private jobsService: JobsService) {}

  postJob(): void {
    this.jobsService.addJob(this.newJob).subscribe({
      next: () => {
        alert('Posted successfully!');
        this.newJob = {}; 
      },
      error: (err) => console.error('Error posting job:', err)
    });
  }
}
