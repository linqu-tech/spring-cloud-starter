import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserDetailComponent } from './detail/user.detail.component';
import { UserListComponent } from './list/user.list.component';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: 'list', component: UserListComponent, pathMatch: 'full' },
      { path: 'detail', component: UserDetailComponent, pathMatch: 'full' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
