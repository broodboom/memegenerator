import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NbCardModule, NbUserModule, NbButtonModule, NbTabsetModule, NbActionsModule, NbRadioModule, NbSelectModule, NbListModule, NbIconModule } from '@nebular/theme';
import { ThemeModule } from 'app/@theme/theme.module';
import { SignupComponent } from './signup.component';

@NgModule({
  declarations: [SignupComponent],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
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
    ReactiveFormsModule
  ]
})
export class SignupModule { }