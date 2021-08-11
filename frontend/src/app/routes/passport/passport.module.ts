import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { PassportCallbackComponent } from './callback/callback.component';
import { PassportOauth2Component } from './oauth2/oauth2.component';

import { PassportRoutingModule } from './passport-routing.module';

@NgModule({
  imports: [
    CommonModule,
    PassportRoutingModule,
  ],
  declarations: [
    PassportOauth2Component,
    PassportCallbackComponent,
  ],
  providers: []
})
export class PassportModule {}
