import { NbMenuItem } from '@nebular/theme';
import { AuthenticationService } from '../temp/authentication.service';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'Home',
    icon: 'home-outline',
    link: '/pages/dashboard',
    home: true,
  },
  {
    title: 'Admin',
    link: '/pages/admin',
  },
  {
    title: 'Log In',
    link: '/pages/login',
  }
];
