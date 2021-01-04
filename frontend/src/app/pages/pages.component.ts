import { Component, OnInit } from '@angular/core';
import { NbMenuItem } from '@nebular/theme';
import { AuthService } from 'app/services/auth.service';

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
export class PagesComponent implements OnInit {

  currentUser: any;
  menu: NbMenuItem[];

  constructor(private authService: AuthService){
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
        icon: 'unlock-outline',
        hidden: this.authService.currentUser()?.role != 'Admin'? true : false,
      },
      {
        title: 'Log In',
        icon: 'power-outline',
        link: '/pages/login',
      },
      {
        title: 'Create',
        icon: 'plus-square-outline',
        link: '/pages/create',
      },
      {
        title: 'Like Button',
        icon: 'heart-outline',
        link: '/pages/likebutton',
      },
      {
        title: 'Profile',
        icon: 'person-outline',
        link: '/pages/profile',
      }
    ];
  }
}
