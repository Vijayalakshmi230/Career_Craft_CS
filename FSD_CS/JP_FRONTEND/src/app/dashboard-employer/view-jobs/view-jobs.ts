import { Component, OnInit } from '@angular/core';
import { JobsService } from '../../service-jobs/jobs';
import { JobDto } from '../../service-jobs/job-model.model';


@Component({
  selector: 'app-view-jobs',
  standalone: false,
  templateUrl: './view-jobs.html',
  styleUrl: './view-jobs.css'
})
export class ViewJobs  implements OnInit {
  allJobs: JobDto[] = [];
  filteredJobs: JobDto[] = [];
  searchTerm: string = '';

  constructor(private jobService: JobsService) {}

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
}