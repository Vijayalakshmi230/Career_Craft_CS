// import { Injectable } from '@angular/core';

// @Injectable({
//   providedIn: 'root'
// })
// export class ResumeService {

// }

// src/app/service-resume/resume.ts
// src/app/service-profile/resume.ts
// import { Injectable } from '@angular/core';
// import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { Observable } from 'rxjs';

// @Injectable({
//   providedIn: 'root'
// })
// export class ResumeService {
//   private resumeUrl = 'http://localhost:8080/resume';

//   constructor(private http: HttpClient) {}

//   private getAuthHeaders(): HttpHeaders {
//     const token = localStorage.getItem('token');
//     return new HttpHeaders({ 'Authorization': `Bearer ${token}` });
//   }

//   uploadResume(file: File, userId: number): Observable<any> {
//     const formData = new FormData();
//     formData.append('file', file);
//     formData.append('userId', userId.toString());
//     return this.http.post(`${this.resumeUrl}/upload`, formData, { headers: this.getAuthHeaders() });
//   }

//   getResume(userId: number): Observable<Blob> {
//     return this.http.get(`${this.resumeUrl}/download/${userId}`, {
//       responseType: 'blob',
//       headers: this.getAuthHeaders()
//     });
//   }

//   // deleteResume(userId: number): Observable<any> {
//   //   return this.http.delete(`${this.resumeUrl}/delete/${userId}`, { headers: this.getAuthHeaders() });
//   // }

//   deleteResume(userId: number): Observable<any> {
//   return this.http.delete(`${this.resumeUrl}/delete/${userId}`, { responseType: 'text' });
// }

// }
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResumeService{
  private resumeUrl = 'http://localhost:8080/resume';  

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : new HttpHeaders();
  }

  uploadResume(file: File, userId: number): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('userId', userId.toString());
    return this.http.post(`${this.resumeUrl}/upload`, formData, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }

  getResume(userId: number): Observable<Blob> {
    return this.http.get(`${this.resumeUrl}/download/${userId}`, {
      headers: this.getAuthHeaders(),
      responseType: 'blob'
    });
  }

  deleteResume(userId: number): Observable<string> {
    return this.http.delete(`${this.resumeUrl}/delete/${userId}`, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }
}