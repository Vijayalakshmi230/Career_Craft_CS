import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-home',
  standalone: false,
  templateUrl: './dashboard-home.html',
  styleUrl: './dashboard-home.css'
})
export class DashboardHome {

  constructor(private router: Router) { }

  goToPostJob() {
    this.router.navigate(['/dashboard-employer/post-job']);
  }

  goToViewJobs() {
    this.router.navigate(['/dashboard-employer/view-jobs']); 
  }

  goToApplicationStatus() {
    this.router.navigate(['/dashboard-employer/application-status']);
  }
}
