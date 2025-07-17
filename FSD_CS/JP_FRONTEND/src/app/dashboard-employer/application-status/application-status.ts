import { Component, OnInit } from '@angular/core';

import { Application, EmployerViewApplicationDto } from '../../service-application/application';
import { ResumeService } from '../../services-resume/resume';

@Component({
  selector: 'app-application-status',
  standalone: false,
  templateUrl: './application-status.html',
  styleUrl: './application-status.css'
})
export class ApplicationStatus implements OnInit {
  applications: EmployerViewApplicationDto[] = [];

  constructor(
    private appService: Application,
    private resumeService: ResumeService
  ) {}

  ngOnInit(): void {
    this.loadApplications();
  }

  loadApplications(): void {
    this.appService.getAllApplicationsForMyJobs().subscribe({
      next: data => {
        this.applications = data;
      },
      error: error => {
        console.error('Error loading applications', error);
      }
    });
  }

  updateStatus(applicationId: number | undefined, status: string): void {
  if (!applicationId) {
    console.error('Invalid application ID');
    return;
  }
  this.appService.updateApplicationStatus(applicationId, status).subscribe({
    next: () => {
      this.loadApplications(); 
    },
    error: error => {
      console.error('Error updating status', error);
    }
  });
}

 downloadResume(userId: number): void {
  this.resumeService.getResume(userId).subscribe({
    next: (blob) => {
      
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'resume';
      a.click();
      window.URL.revokeObjectURL(url);
    },
    error: (error) => {
      if (error.status === 404) {
        alert('Resume not uploaded by the user.');
      } else {
        console.error('Error downloading resume', error);
      }
    }
  });
}


}