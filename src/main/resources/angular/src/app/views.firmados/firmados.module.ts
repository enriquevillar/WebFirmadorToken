import { NgModule } from '@angular/core';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import {
  AvatarModule,
  ButtonGroupModule,
  ButtonModule,
  CardModule,
  FormModule,
  GridModule, ModalModule,
  NavModule,
  ProgressModule, SpinnerModule,
  TableModule,
  TabsModule,
} from '@coreui/angular-pro';

import { IconModule } from '@coreui/icons-angular';

import { FirmadosRoutingModule } from './firmados-routing.module';
import {PdfViewerModule} from "ng2-pdf-viewer";
import { FirmadosComponent } from './firmados.component';

@NgModule({
  imports: [
    FirmadosRoutingModule,
    CardModule,
    NavModule,
    IconModule,
    TabsModule,
    CommonModule,
    GridModule,
    ProgressModule,
    ReactiveFormsModule,
    ButtonModule,
    FormModule,
    ButtonModule,
    ButtonGroupModule,
    AvatarModule,
    TableModule,
    ModalModule,
    PdfViewerModule,
    SpinnerModule,
  ],
  declarations: [FirmadosComponent]
})
export class FirmadosModule {
}
