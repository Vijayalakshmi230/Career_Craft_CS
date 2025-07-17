import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApplicationDto } from './application-model';
import { JobDto } from '../service-jobs/job-model.model';

export interface JobSeekerApplicationViewDto {
  application: ApplicationDto;
  job: JobDto;

}

export interface EmployerViewApplicationDto {
  application: ApplicationDto;
  job: JobDto;
  resumeDownloadUrl: string;
  hasResume?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class Application {
  apiUrl = 'http://localhost:8080';

  private baseUrl = 'http://localhost:8080/api/applications';

  constructor(private http: HttpClient) { }

  private authHeaders() {
    const token = localStorage.getItem('token');
    return token ? { headers: new HttpHeaders({ Authorization: `Bearer ${token}` }) } : {};
  }

  getAllApplicationsForMyJobs(): Observable<EmployerViewApplicationDto[]> {
    return this.http.get<EmployerViewApplicationDto[]>(`${this.baseUrl}/employer/my`, this.authHeaders());
  }


  updateApplicationStatus(applicationId: number, status: string): Observable<ApplicationDto> {
    return this.http.put<ApplicationDto>(
      `${this.baseUrl}/${applicationId}/status?status=${status}`,
      {},
      this.authHeaders()
    );
  }


applyToJob(jobId: number, data: ApplicationDto): Observable<ApplicationDto> {
  return this.http.post<ApplicationDto>(`${this.baseUrl}/job/${jobId}`, data, this.authHeaders());
}

  getMyApplications(): Observable<JobSeekerApplicationViewDto[]> {
    return this.http.get<JobSeekerApplicationViewDto[]>(`${this.baseUrl}/my`, this.authHeaders());
  }


  updateMyApplication(applicationId: number, data: ApplicationDto): Observable<ApplicationDto> {
    return this.http.put<ApplicationDto>(`${this.baseUrl}/${applicationId}`, data, this.authHeaders());
  }


  deleteMyApplication(applicationId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${applicationId}`, this.authHeaders());
  }

  getApplicationsForJob(jobId: number): Observable<EmployerViewApplicationDto[]> {
    return this.http.get<EmployerViewApplicationDto[]>(`${this.baseUrl}/job/${jobId}`, this.authHeaders());
  }
}