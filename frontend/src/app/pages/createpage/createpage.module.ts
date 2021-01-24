import { NgModule } from '@angular/core';
import {
  NbActionsModule,
  NbButtonModule,
  NbCardModule,
  NbTabsetModule,
  NbUserModule,
  NbRadioModule,
  NbSelectModule,
  NbListModule,
  NbIconModule,
  NbAutocompleteModule,
} from '@nebular/theme';
import { HttpClientModule } from '@angular/common/http'

import { ThemeModule } from '../../@theme/theme.module';
import { CreatepageComponent } from './createpage.component';
import { FormsModule } from '@angular/forms';
import { NotifierModule } from 'angular-notifier';

@NgModule({
  imports: [
    FormsModule,
    ThemeModule,
    NbCardModule,
    NbUserModule,
    NbButtonModule,
    NbTabsetModule,
    NbActionsModule,
    NbRadioModule,
    NbSelectModule,
    NbListModule,
    NbIconModule,
    NbButtonModule,
    HttpClientModule,
    NbAutocompleteModule,
    NotifierModule
  ],
  declarations: [
    CreatepageComponent
  ],
})
export class CreatePageModule { }