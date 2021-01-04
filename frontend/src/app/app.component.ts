import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Role, User } from './models/User';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'ngx-app',
  template: '<router-outlet></router-outlet>',
})
export class AppComponent implements OnInit {
  // These imports are added for Admin Page without LogIn or register features
  currentUser: User;

  // Constructor was empty before Admin Page test
  constructor(
    private router: Router,
    private authService: AuthService
  ) {
  }

  // The next 2 functions are added for Admin Page without LogIn or register features
  get isAdmin() {
    return this.authService.currentUser() && this.authService.currentUser().role === Role.Admin;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/pages/login']);
  }

  ngOnInit(): void {

  }
}
