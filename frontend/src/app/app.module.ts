import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ThemeModule } from './@theme/theme.module';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import {
  NbDatepickerModule,
  NbDialogModule,
  NbMenuModule,
  NbSidebarModule,
  NbToastrModule,
  NbWindowModule,
} from '@nebular/theme';
import { NbSecurityModule, NbRoleProvider } from '@nebular/security';
import { of } from 'rxjs';

@NgModule({
  declarations: [AppComponent],
  imports: [
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
          view: ['dashboard', 'create'],
        },
        user: {
          parent: 'guest',
          create: '',
        },
        moderator: {
          parent: 'user',
          create: '',
          remove: '*',
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
          return of('guest'); // TODO get the role from the user none == guest
        },
      },
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
