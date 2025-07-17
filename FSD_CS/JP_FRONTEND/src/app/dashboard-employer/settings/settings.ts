import { Component } from '@angular/core';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-settings',
  standalone: false,
  templateUrl: './settings.html',
  styleUrl: './settings.css'
})
export class Settings {
  oldPassword = '';
  newPassword = '';
  successMessage = '';

  constructor(private auth: AuthService) {}

  changePassword() {
    this.auth.changePassword(this.oldPassword, this.newPassword).subscribe({
      next: () => {
        this.successMessage = 'Password changed successfully!';
        this.oldPassword = '';
        this.newPassword = '';
      },
      error: () => {
        alert('Failed to change password');
      }
    });
  }

  logout() {
    this.auth.logout();
  }

  deleteAccount() {
    if (confirm('Are you sure? This cannot be undone.')) {
      this.auth.deleteAccount().subscribe({
        next: () => {
          alert('Account deleted');
          this.auth.logout();
        },
        error: () => {
          alert('Failed to delete account');
        }
      });
    }
  }
}