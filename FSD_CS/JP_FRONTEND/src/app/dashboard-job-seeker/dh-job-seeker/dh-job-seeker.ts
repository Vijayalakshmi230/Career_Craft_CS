import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dh-job-seeker',
  standalone: false,
  templateUrl: './dh-job-seeker.html',
  styleUrl: './dh-job-seeker.css'
})
export class DhJobSeeker {

  constructor(private router: Router) { }

  goToViewJobs() {
    this.router.navigate(['/dashboard-job-seeker/view-all-jobs']); // adjust path to your route
  }

}
