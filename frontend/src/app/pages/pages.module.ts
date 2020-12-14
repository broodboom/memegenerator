import { NgModule } from '@angular/core';
import { NbMenuModule } from '@nebular/theme';

import { ThemeModule } from '../@theme/theme.module';
import { PagesComponent } from './pages.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { AdminModule } from './admin/admin.module';
import { LoginModule } from '../temp/login.module';
import { PagesRoutingModule } from './pages-routing.module';
import { DetailpageComponent } from './detailpage/detailpage.component';
import { LikeButtonModule } from './likebutton/likebutton.module';
import { CreatePageModule } from './createpage/createpage.module';

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModule,
    AdminModule,
    LoginModule,
    LikeButtonModule,
    CreatePageModule
  ],
  declarations: [
    PagesComponent,
    DetailpageComponent
  ],
})
export class PagesModule {
}
