import { NgModule } from '@angular/core';
import { NbMenuModule } from '@nebular/theme';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ThemeModule } from '../@theme/theme.module';
import { PagesComponent } from './pages.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { AdminModule } from './admin/admin.module';
import { LoginModule } from '../temp/login.module';
import { PagesRoutingModule } from './pages-routing.module';
import { DetailpageComponent } from './detailpage/detailpage.component';
import { CreatepageComponent } from './createpage/createpage.component';
import { LikeButtonModule } from './likebutton/likebutton.module';
import { ProfileComponent } from './profile/profile.component';

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModule,
    AdminModule,
    LoginModule,
    LikeButtonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  declarations: [
    PagesComponent,
    DetailpageComponent,
    CreatepageComponent,
    ProfileComponent
  ],
})
export class PagesModule {
}
