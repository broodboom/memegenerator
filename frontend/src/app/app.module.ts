import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
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

// MUST DELETE these imports after merge
import { fakeBackendProvider } from './temp/fake-backend';
import { JwtInterceptor} from './temp/jwt.interceptor';
import { MemeService } from './_helpers/MemeService';
import { Interceptor } from './_helpers/interceptor';

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
    // Added for Admin Page without login/register features, untill fakeBackendProvider
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },

    // provider used to create fake backend, MUST DELETE after merge
    fakeBackendProvider,

    
    //CRUD service for the memes
    {provide: MemeService,
     },
     {provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true},
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
