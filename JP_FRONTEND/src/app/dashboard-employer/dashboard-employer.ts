import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileService } from '../service-profile/profile';


@Component({
  selector: 'app-dashboard-employer',
  standalone: false,
  templateUrl: './dashboard-employer.html',
  styleUrl: './dashboard-employer.css'
})
export class DashboardEmployer implements OnInit {
  userName = '';
  hover = false; 

  constructor(
  private router: Router,
  private profile: ProfileService,
) {}

ngOnInit() {
  this.profile.getProfile().subscribe({
    next: (profile) => this.userName = profile.name,
    error: (err) => console.error('Failed to load profile', err)
  });
}

  goToProfile() {
    this.router.navigate(['/dashboard-employer/user-profile']);
  }

  goToViewJobs() {
    this.router.navigate(['/dashboard-employer/view-my-jobs']);
  }

  goToSettings() {
    this.router.navigate(['/dashboard-employer/settings']);
  }

  goToDashboard() {
    this.router.navigate(['/dashboard-employer']);
  }

}
