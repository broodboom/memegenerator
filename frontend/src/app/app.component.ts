import { Component, OnInit } from '@angular/core';

// These imports are added for Admin Page without LogIn or register features
import { Router } from '@angular/router';
import { AuthenticationService } from './temp/authentication.service';

// The Role and User vars are used for Admin Page without login or register features, MUST DELETE after merge
enum Role {
  User = 'User',
  Admin = 'Admin'
}

class User {
  id: number;
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  role: Role;
  token?: string;
}

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
    private authenticationService: AuthenticationService
  ) {
      this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  // The next 2 functions are added for Admin Page without LogIn or register features
  get isAdmin() {
    return this.currentUser && this.currentUser.role === Role.Admin;
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/pages/login']);
  }

  ngOnInit(): void {
  }
}