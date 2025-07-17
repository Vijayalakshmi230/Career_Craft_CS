import { Component, OnInit } from '@angular/core';
import { Application, JobSeekerApplicationViewDto } from '../../service-application/application';

declare var bootstrap: any;

@Component({
  selector: 'app-view-my-applications-job-seeker',
  standalone: false,
  templateUrl: './view-my-applications-job-seeker.html',
  styleUrl: './view-my-applications-job-seeker.css'
})
export class ViewMyApplicationsJobSeeker implements OnInit {

  applications: JobSeekerApplicationViewDto[] = [];
  editApp: any = {}; 
  successMessage: string = '';

  constructor(private appService: Application) {}

  ngOnInit(): void {
    this.loadApplications();
  }

  loadApplications() {
    this.appService.getMyApplications().subscribe(
      data => this.applications = data,
      err => console.error(err)
    );
  }

  openEditModal(application: any) {
    this.editApp = { ...application };
    const modalEl = document.getElementById('editModal')!;
    const modal = new bootstrap.Modal(modalEl);
    modal.show();
  }

  closeModal() {
    const modalEl = document.getElementById('editModal')!;
    const modal = bootstrap.Modal.getInstance(modalEl);
    modal.hide();
  }

  updateApplication() {
    if (!this.editApp.id) {
      console.error('Application id missing');
      return;
    }
    this.appService.updateMyApplication(this.editApp.id, this.editApp).subscribe(
      () => {
        this.successMessage = 'Updated successfully!';
        this.closeModal();
        this.loadApplications();
      },
      err => console.error(err)
    );
  }

  confirmDelete(applicationId: number | undefined) {
    if (!applicationId) {
      console.error('Application id missing');
      return;
    }
    if (confirm('Are you sure you want to delete this application?')) {
      this.appService.deleteMyApplication(applicationId).subscribe(
        () => {
          this.successMessage = 'Deleted successfully!';
          this.loadApplications();
        },
        err => console.error(err)
      );
    }
  }
}