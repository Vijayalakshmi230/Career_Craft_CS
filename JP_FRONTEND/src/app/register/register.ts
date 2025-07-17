import { Component } from '@angular/core';
import { AuthService } from '../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {
  name = '';
  email = '';
  password = '';
  role = 'Employer'; 

  constructor(private auth: AuthService, private router: Router) {}

  register() {
    const data = { name: this.name, email: this.email, password: this.password, role: this.role };
    this.auth.register(data).subscribe({
      next: () => {
        console.log('Registration successful');
        alert('Registration successful! Please login.');
        this.router.navigate(['/login']);
      },
      error: (err: any) => {
        console.error('Registration failed', err);
        alert('Registration failed. Try again.');
      }
    });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
