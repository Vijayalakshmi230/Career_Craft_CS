import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../../service-profile/profile';
import { ResumeService } from '../../services-resume/resume';

@Component({
  selector: 'app-user-profile',
  standalone: false,
  templateUrl: './user-profile.html',
  styleUrl: './user-profile.css'
})
export class UserProfile implements OnInit {
  profile: any = {};
  userId: number | null = null;
  resumeUrl: string | null = null;

  editData: any = {};          
  editing: boolean = false;    
  newResumeFile: File | null = null;

  constructor(
    private profiles: ProfileService,
    private resume: ResumeService
  ) {}

  ngOnInit() {
    this.loadProfile();
  }

  loadProfile() {
    this.profiles.getProfile().subscribe({
      next: data => {
        this.profile = data;
        this.userId = data.id;
        this.loadResume();
      },
      error: err => console.error('Failed to load profile', err)
    });
  }

  loadResume() {
    if (this.userId) {
      this.resume.getResume(this.userId).subscribe({
        next: res => {
          if (this.resumeUrl) URL.revokeObjectURL(this.resumeUrl);
          this.resumeUrl = URL.createObjectURL(res);
        },
        error: () => this.resumeUrl = null
      });
    }
  }

  startEdit() {
    this.editing = true;
    this.editData = {
      name: this.profile.name,
      email: this.profile.email,
      profileDescription: this.profile.profileDescription
    };
    this.newResumeFile = null;
  }

  cancelEdit() {
    this.editing = false;
    this.editData = {};
    this.newResumeFile = null;
  }

  onFileSelected(event: any) {
    this.newResumeFile = event.target.files[0];
  }

  saveEdit() {
  // build updated data: keep old values if fields are empty
  const dataToUpdate = {
    name: this.editData.name?.trim() !== '' ? this.editData.name : this.profile.name,
    email: this.editData.email?.trim() !== '' ? this.editData.email : this.profile.email,
    profileDescription: this.editData.profileDescription?.trim() !== '' 
                        ? this.editData.profileDescription 
                        : this.profile.profileDescription
  };

  this.profiles.updateProfile(dataToUpdate).subscribe({
    next: () => {
      if (this.newResumeFile && this.userId) {
        this.resume.uploadResume(this.newResumeFile, this.userId).subscribe({
          next: () => this.loadProfile(),
          error: err => console.error('Resume upload failed', err)
        });
      } else {
        this.loadProfile();
      }
    },
    error: err => console.error('Failed to update profile', err)
  });
}


  deleteResume() {
    if (this.userId) {
      this.resume.deleteResume(this.userId).subscribe({
        next: () => {
          this.resumeUrl = null;
          this.loadProfile();
        },
        error: err => console.error('Delete failed', err)
      });
    }
  }
}
