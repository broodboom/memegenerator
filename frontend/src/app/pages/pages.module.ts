import { NgModule } from "@angular/core";
import { NbMenuModule } from "@nebular/theme";

import { ThemeModule } from "../@theme/theme.module";
import { PagesComponent } from "./pages.component";
import { DashboardModule } from "./dashboard/dashboard.module";
import { AdminModule } from "./admin/admin.module";
import { LoginModule } from "./login/login.module";
import { PagesRoutingModule } from "./pages-routing.module";
import { DetailpageComponent } from "./detailpage/detailpage.component";
import { CreatepageComponent } from "./createpage/createpage.component";
import { LikeButtonModule } from "./likebutton/likebutton.module";

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModule,
    AdminModule,
    LoginModule,
    LikeButtonModule,
  ],
  declarations: [PagesComponent, DetailpageComponent, CreatepageComponent],
})
export class PagesModule {}
