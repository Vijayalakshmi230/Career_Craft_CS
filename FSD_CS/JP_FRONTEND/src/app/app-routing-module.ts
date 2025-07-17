import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './login/login';
import { DashboardEmployer } from './dashboard-employer/dashboard-employer';
import { DashboardJobSeeker } from './dashboard-job-seeker/dashboard-job-seeker';
import { Register } from './register/register';
import { UserProfile } from './dashboard-employer/user-profile/user-profile';
import { ViewMyJobs } from './dashboard-employer/view-my-jobs/view-my-jobs';
import { Settings } from './dashboard-employer/settings/settings';
import { AuthGuard } from './services/auth-guard';
import { UserProfileJobSeeker } from './dashboard-job-seeker/user-profile-job-seeker/user-profile-job-seeker';
import { ViewMyApplicationsJobSeeker } from './dashboard-job-seeker/view-my-applications-job-seeker/view-my-applications-job-seeker';
import { SettingsJobSeeker } from './dashboard-job-seeker/settings-job-seeker/settings-job-seeker';
import { PostJobs } from './dashboard-employer/post-jobs/post-jobs';
import { DashboardHome } from './dashboard-employer/dashboard-home/dashboard-home';
import { ViewJobs } from './dashboard-employer/view-jobs/view-jobs';
import { ApplicationStatus } from './dashboard-employer/application-status/application-status';
import { DhJobSeeker } from './dashboard-job-seeker/dh-job-seeker/dh-job-seeker';
import { ViewAllJobs } from './dashboard-job-seeker/view-all-jobs/view-all-jobs';

const routes: Routes = [
  { path: '', component: Login },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  {
    path: 'dashboard-employer',
    component: DashboardEmployer,
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      
      { path: '', component: DashboardHome }, // default child route
      { path: 'user-profile', component: UserProfile },
      { path: 'view-my-jobs', component: ViewMyJobs },
      { path: 'settings', component: Settings },
      { path: 'post-job', component: PostJobs },
      { path: 'view-jobs', component: ViewJobs },
      { path: 'application-status', component: ApplicationStatus },
    ]
  },

  {
    path: 'dashboard-job-seeker',
    component: DashboardJobSeeker,
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      { path: '', component: DhJobSeeker },
      { path: 'user-profile-job-seeker', component: UserProfileJobSeeker },
      { path: 'view-my-application-job-seeker', component: ViewMyApplicationsJobSeeker },
      { path: 'settings-job-seeker', component: SettingsJobSeeker },
      { path: 'view-all-jobs', component: ViewAllJobs },
    ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
