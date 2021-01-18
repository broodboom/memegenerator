import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule } from "@angular/core";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { ThemeModule } from "./@theme/theme.module";
import { AppComponent } from "./app.component";
import { AppRoutingModule } from "./app-routing.module";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  NbDatepickerModule,
  NbDialogModule,
  NbMenuModule,
  NbSidebarModule,
  NbToastrModule,
  NbWindowModule,
} from "@nebular/theme";
import { NbSecurityModule, NbRoleProvider } from "@nebular/security";
import { of } from "rxjs";

import { MemeService } from './services/meme/meme.service';
import { SignupComponent } from './pages/signup/signup.component';

@NgModule({
  declarations: [AppComponent, SignupComponent],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    NbSidebarModule.forRoot(),
    NbMenuModule.forRoot(),
    NbDatepickerModule.forRoot(),
    NbDialogModule.forRoot(),
    NbWindowModule.forRoot(),
    NbToastrModule.forRoot(),
    ThemeModule.forRoot(),
    NbSecurityModule.forRoot({
      accessControl: {
        guest: {
          view: ["dashboard", "create"],
        },
        user: {
          parent: "guest",
          create: "",
        },
        moderator: {
          parent: "user",
          create: "",
          remove: "*",
        },
      },
    }),
  ],
  providers: [
    // ...
    {
      provide: NbRoleProvider,
      useValue: {
        getRole: () => {
          return of("guest"); // TODO get the role from the user none == guest
        },
      },
    },
    { provide: MemeService },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
