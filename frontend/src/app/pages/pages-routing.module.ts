import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";

import { PagesComponent } from "./pages.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { AdminComponent } from "./admin/admin.component";
import { CreatepageComponent } from "./createpage/createpage.component";

import { AuthGuard } from "../_helpers/auth.guard";
import { LikeButtonComponent } from "./likebutton/likebutton.component";
import { LoginComponent } from "./login/login.component";
import { SignupComponent } from "./signup/signup.component";
import { ProfileComponent } from "./profile/profile.component";

const routes: Routes = [
  {
    path: "",
    component: PagesComponent,
    children: [
      {
        path: "dashboard",
        component: DashboardComponent,
      },
      {
        path: "admin",
        component: AdminComponent,
        canActivate: [AuthGuard],
        data: { roles: ["admin"] },
      },
      {
        path: "login",
        component: LoginComponent,
      },
      {
        path: "create",
        component: CreatepageComponent,
        data: { roles: ["admin", "user"] },
      },
      {
        path: "likebutton",
        component: LikeButtonComponent,
        data: { roles: ["admin", "user"] },
      },
      {
        path: "signup",
        component: SignupComponent,
        data: { roles: ["admin", "user"] },
      },
      {
        path: "profile",
        component: ProfileComponent,
        data: { roles: ["admin", "user"] },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {}
