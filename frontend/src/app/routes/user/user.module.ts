import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzPageHeaderModule } from 'ng-zorro-antd/page-header';
import { NzTableModule } from 'ng-zorro-antd/table';
import { UserService } from './core/user.service';
import { UserDetailComponent } from './detail/user.detail.component';
import { UserListComponent } from './list/user.list.component';
import { UserRoutingModule } from './user-routing.module';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzDescriptionsModule } from 'ng-zorro-antd/descriptions';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzDividerModule } from 'ng-zorro-antd/divider';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NzButtonModule,
    NzCardModule,
    NzDescriptionsModule,
    NzDividerModule,
    NzFormModule,
    NzInputModule,
    NzPageHeaderModule,
    NzPopconfirmModule,
    NzRadioModule,
    NzSelectModule,
    NzTableModule,
    ReactiveFormsModule,
    UserRoutingModule,
  ],
  providers: [UserService],
  declarations: [UserListComponent, UserDetailComponent],
})
export class UserModule {}
