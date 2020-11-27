import { NgModule } from '@angular/core';
import { NbMenuModule } from '@nebular/theme';

import { ThemeModule } from '../@theme/theme.module';
import { PagesComponent } from './pages.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { PagesRoutingModule } from './pages-routing.module';
import { DetailpageComponent } from './detailpage/detailpage.component';
import { CreatepageComponent } from './createpage/createpage.component';

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModule
  ],
  declarations: [
    PagesComponent,
    DetailpageComponent,
    CreatepageComponent
  ],
})
export class PagesModule {
}
