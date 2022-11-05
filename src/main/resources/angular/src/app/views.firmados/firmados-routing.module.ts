import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {FirmadosComponent} from "./firmados.component";

const routes: Routes = [
  {
    path: '',
    component: FirmadosComponent,
    data: {
      title: $localize`Firmados`
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FirmadosRoutingModule {
}
