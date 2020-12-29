import { NbMenuItem } from '@nebular/theme';

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
  },
  {
    title: 'Create',
    icon: 'cogwheel',
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
