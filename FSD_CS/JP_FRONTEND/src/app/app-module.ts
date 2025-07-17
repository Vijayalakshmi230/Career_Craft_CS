import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing-module';
import { FormsModule } from '@angular/forms';
import { App } from './app';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { Login } from './login/login';
import { Register } from './register/register';
import { DashboardJobSeeker } from './dashboard-job-seeker/dashboard-job-seeker';
import { UserProfile } from './dashboard-employer/user-profile/user-profile';
import { Settings } from './dashboard-employer/settings/settings';
import { ViewMyJobs } from './dashboard-employer/view-my-jobs/view-my-jobs';
import { DashboardEmployer } from './dashboard-employer/dashboard-employer';
import { UserProfileJobSeeker } from './dashboard-job-seeker/user-profile-job-seeker/user-profile-job-seeker';
import { ViewMyApplicationsJobSeeker } from './dashboard-job-seeker/view-my-applications-job-seeker/view-my-applications-job-seeker';
import { SettingsJobSeeker } from './dashboard-job-seeker/settings-job-seeker/settings-job-seeker';
import { PostJobs } from './dashboard-employer/post-jobs/post-jobs';
import { DashboardHome } from './dashboard-employer/dashboard-home/dashboard-home';
import { ViewJobs } from './dashboard-employer/view-jobs/view-jobs';
import { ApplicationStatus } from './dashboard-employer/application-status/application-status';
import { ViewAllJobs } from './dashboard-job-seeker/view-all-jobs/view-all-jobs';
import { DhJobSeeker } from './dashboard-job-seeker/dh-job-seeker/dh-job-seeker';

@NgModule({
  declarations: [
    App,
    Login,
    Register,
    DashboardEmployer,
    DashboardJobSeeker,
    UserProfile,
    Settings,
    ViewMyJobs,
    UserProfileJobSeeker,
    ViewMyApplicationsJobSeeker,
    SettingsJobSeeker,
    PostJobs,
    DashboardHome,
    ViewJobs,
    ApplicationStatus,
    ViewAllJobs,
    DhJobSeeker
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient(withFetch())
  ],
  bootstrap: [App]
})
export class AppModule { }
