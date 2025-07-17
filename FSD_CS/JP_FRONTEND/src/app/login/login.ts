import { Component } from '@angular/core';
import { AuthService } from '../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  email = '';
  password = '';

  constructor(private auth: AuthService, private router: Router) {}

  login() {
    this.auth.login(this.email, this.password).subscribe({
      next: (res: any) => {
        this.auth.saveToken(res.token);
        this.auth.saveUserRole(res.role); 
        console.log('Login successful, role:', res.role);
        if (res.role === 'Employer') {
          this.router.navigate(['/dashboard-employer']);
        } else if (res.role === 'Job_Seeker') {
          this.router.navigate(['/dashboard-job-seeker']);
        } else {
          console.error('Unknown role');
        }
      },
      error: (err: any) => {
        console.error('Login failed', err);
        alert('Login failed. Please check your credentials.');
      }
    });
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}
