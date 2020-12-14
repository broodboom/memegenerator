import { Component, OnInit } from '@angular/core';
import { NbMenuItem } from '@nebular/theme';

import { MENU_ITEMS } from './pages-menu';

@Component({
  selector: 'ngx-pages',
  styleUrls: ['pages.component.scss'],
  template: `
    <ngx-one-column-layout>
      <nb-menu [items]="menu"></nb-menu>
      <router-outlet></router-outlet>
    </ngx-one-column-layout>
  `,
})
export class PagesComponent /*implements OnInit*/{

  /*currentUser: any;
  menu: NbMenuItem[];

  constructor(private AuthService: AuthenticationService){
    this.currentUser = AuthService.currentUserValue;
  }

  ngOnInit(){
    this.menu = [
      {
        title: 'Home',
        icon: 'home-outline',
        link: '/pages/dashboard',
        home: true,
      },
      {
        title: 'Admin',
        link: '/pages/admin',
        hidden: this.currentUser.Role != 'Admin'? true:false,
      },
      {
        title: 'Log In',
        link: '/pages/login',
      }
    ]
  } */
  menu = MENU_ITEMS;
}
