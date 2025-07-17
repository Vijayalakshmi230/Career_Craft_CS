import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JobDto } from './job-model.model';

@Injectable({
  providedIn: 'root'
})
export class JobsService {
  private baseUrl = 'http://localhost:8080/api/jobs';

  constructor(private http: HttpClient) {}

  private authHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : new HttpHeaders();
  }


  getMyJobs(): Observable<JobDto[]> {
    return this.http.get<JobDto[]>(`${this.baseUrl}/my`, { headers: this.authHeaders() });
  }

  
  getAllJobs(): Observable<JobDto[]> {
    return this.http.get<JobDto[]>(this.baseUrl, { headers: this.authHeaders() });
  }

 
  addJob(job: JobDto): Observable<JobDto> {
    return this.http.post<JobDto>(this.baseUrl, job, { headers: this.authHeaders() });
  }

  
  updateJob(jobId: number, job: Partial<JobDto>): Observable<JobDto> {
    return this.http.put<JobDto>(`${this.baseUrl}/${jobId}`, job, { headers: this.authHeaders() });
  }


  deleteJob(jobId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${jobId}`, { headers: this.authHeaders() });
  }

 
  getJobById(jobId: number): Observable<JobDto> {
    return this.http.get<JobDto>(`${this.baseUrl}/${jobId}`, { headers: this.authHeaders() });
  }

 
  searchByDesignation(designation: string): Observable<JobDto[]> {
    return this.http.get<JobDto[]>(
      `${this.baseUrl}/search/designation?designation=${designation}`,
      { headers: this.authHeaders() }
    );
  }


  searchByCompany(company: string): Observable<JobDto[]> {
    return this.http.get<JobDto[]>(
      `${this.baseUrl}/search/company?company=${company}`,
      { headers: this.authHeaders() }
    );
  }
}
