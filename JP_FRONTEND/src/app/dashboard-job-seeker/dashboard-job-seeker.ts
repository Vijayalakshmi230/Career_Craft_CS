import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileService } from '../service-profile/profile';

@Component({
  selector: 'app-dashboard-job-seeker',
  standalone: false,
  templateUrl: './dashboard-job-seeker.html',
  styleUrl: './dashboard-job-seeker.css'
})
export class DashboardJobSeeker implements OnInit {
  userName = '';

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

  goToProfileJobSeeker() {
    this.router.navigate(['/dashboard-job-seeker/user-profile-job-seeker']);
  }

  goToViewApplicationJobSeeker() {
    this.router.navigate(['/dashboard-job-seeker/view-my-application-job-seeker']);
  }

  goToSettingsJobSeeker() {
    this.router.navigate(['/dashboard-job-seeker/settings-job-seeker']);
  }

  goToDashboardJobSeeker() {
    this.router.navigate(['/dashboard-job-seeker']);
  }
}
