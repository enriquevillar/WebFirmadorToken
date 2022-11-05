import { INavData } from '@coreui/angular-pro';

export const navItems: INavData[] = [
  {
    name: $localize`Para Firma`,
    url: '/dashboard',
    iconComponent: { name: 'cilPencil' },
    badge: {
      color: 'info',
      text: $localize``,
      size:'xs'
    }
  },
  {
    name: $localize`Firmados`,
    url: '/firmados',
    iconComponent: { name: 'cilPaperPlane' },
     badge: {
      color: 'info',
      text: $localize``,
      size:'xs'
    }
  }
];
