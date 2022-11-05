import {APP_INITIALIZER, NgModule} from '@angular/core';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { BrowserModule, Title } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { PdfViewerModule } from 'ng2-pdf-viewer';



export function initConfig(appConfig: AppConfigService) {
  return () => appConfig.loadConfig();
}
// Import routing module
import { AppRoutingModule } from './app-routing.module';

// Import app component
import { AppComponent } from './app.component';

// Import containers
import {
  DefaultAsideComponent,
  DefaultFooterComponent,
  DefaultHeaderComponent,
  DefaultLayoutComponent
} from './containers';

import {
  BadgeModule,
  BreadcrumbModule,
  ButtonModule,
  FooterModule,
  GridModule,
  HeaderModule,
  NavModule,
  SidebarModule, SpinnerModule
} from '@coreui/angular-pro';

import { IconModule, IconSetService } from '@coreui/icons-angular';
import {AppConfigService} from "./providers/app-config-service.service";

const APP_CONTAINERS = [
  DefaultAsideComponent,
  DefaultFooterComponent,
  DefaultHeaderComponent,
  DefaultLayoutComponent
];

@NgModule({
  declarations: [AppComponent,...APP_CONTAINERS],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    BreadcrumbModule,
    FooterModule,
    GridModule,
    HeaderModule,
    SidebarModule,
    IconModule,
    NavModule,
    ButtonModule,
    SidebarModule,
    BadgeModule,
    HttpClientModule,
    PdfViewerModule,
    SpinnerModule
  ],
  providers: [
   // LoaderService,
    // { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true },
    HttpClientModule,
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    },
    {
      provide: APP_INITIALIZER,
      useFactory: initConfig,
      deps: [AppConfigService],
      multi: true,
    },
    IconSetService,
    Title
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
