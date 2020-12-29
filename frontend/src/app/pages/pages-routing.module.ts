import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdminComponent } from './admin/admin.component';
import {CreatepageComponent } from './createpage/createpage.component';

import { AuthGuard } from '../_helpers/auth.guard';
import { LikeButtonComponent } from './likebutton/likebutton.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ProfileComponent } from './profile/profile.component';

// These lines are only used for the Admin Page WITHOUT the logIn/register features, so MUST DELETE after merge
enum Role {
  User = 'User',
  Admin = 'Admin'
}
// Untill here

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'dashboard',
      component: DashboardComponent,
    },
    {
      path: 'admin',
      component: AdminComponent,
      canActivate: [AuthGuard],
      data: { roles: [Role.Admin] },
    },
    {
      path: 'login',
      component: LoginComponent,
    },
    {
      path: 'create',
      component: CreatepageComponent,
    },
    {
      path: 'likebutton',
      component: LikeButtonComponent,
    },
    {
      path: 'signup',
      component: SignupComponent,
    },
    {
      path: 'profile',
      component: ProfileComponent,
    }
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
