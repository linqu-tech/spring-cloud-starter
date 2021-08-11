import { registerLocaleData } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import en from '@angular/common/locales/en';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import {
  AuthorizeGuard,
  AuthService,
  HttpService,
  LocalStorageService,
  ResponseInterceptor,
  TokenInterceptor,
} from '@core';
import { en_US, NZ_I18N } from 'ng-zorro-antd/i18n';
import { NzNotificationModule } from 'ng-zorro-antd/notification';

import { AppComponent } from './app.component';
import { CoreAuthModule } from './core/auth/auth.module';
import { IconsProviderModule } from './icons-provider.module';
import { LayoutModule } from './layout/layout.module';
import { RoutesModule } from './routes/routes.module';

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    CoreAuthModule,
    FormsModule,
    HttpClientModule,
    IconsProviderModule,
    LayoutModule,
    NzNotificationModule,
    RouterModule,
    RoutesModule,
  ],
  providers: [
    AuthService,
    AuthorizeGuard,
    HttpService,
    LocalStorageService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ResponseInterceptor, multi: true },
    { provide: NZ_I18N, useValue: en_US },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
