import { Component } from '@angular/core';

import { navItems } from './_nav';
import {brandSet, cilListNumbered, cilPaperPlane,cilPencil,cilPageview} from "@coreui/icons-pro";
import {IconSetService} from "@coreui/icons-angular";

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html'
})
export class DefaultLayoutComponent {

  constructor(public iconSet: IconSetService) {
    iconSet.icons = { cilListNumbered, cilPaperPlane,cilPageview,cilPencil, ...brandSet };

  }

  public navItems = navItems;
}
