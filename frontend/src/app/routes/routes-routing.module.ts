import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthorizeGuard } from '@core';
import { LayoutBasicComponent } from '../layout/basic/basic.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutBasicComponent,
    canActivate: [AuthorizeGuard],
    canActivateChild: [AuthorizeGuard],
    data: {},
    children: [
      { path: '', redirectTo: '/user/list', pathMatch: 'full' },
      { path: 'user', loadChildren: () => import('./user/user.module').then(m => m.UserModule) },
    ],
  },
  {
    path: '',
    loadChildren: () => import('./passport/passport.module').then((m) => m.PassportModule)
  },
  {
    path: 'exception',
    loadChildren: () => import('./exception/exception.module').then((m) => m.ExceptionModule)
  },
  { path: '**', redirectTo: 'exception/404' },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      useHash: false
    }),
  ],
  exports: [RouterModule],
})
export class RouteRoutingModule {
}
