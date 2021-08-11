import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LayoutPassportComponent } from '../../layout/passport/passport.component';
import { PassportCallbackComponent } from './callback/callback.component';
import { PassportOauth2Component } from './oauth2/oauth2.component';

const routes: Routes = [
  {
    path: 'passport',
    component: LayoutPassportComponent,
    children: [
      {
        path: 'oauth2',
        component: PassportOauth2Component,
        data: { title: '注册结果', titleI18n: 'app.register.register' },
      },
    ],
  },
  { path: 'passport/callback', component: PassportCallbackComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PassportRoutingModule {}
