import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdminComponent } from './admin/admin.component';

import { AuthGuard } from '../_helpers/auth.guard';
// This is a temp LogIn, MUST DELETE after merge
import { LoginComponent } from '../temp/login.component';

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
    }
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
