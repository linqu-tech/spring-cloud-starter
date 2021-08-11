import { NgModule } from '@angular/core';

import { RouteRoutingModule } from './routes-routing.module';
import { FormBuilder } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';

@NgModule({
  imports: [RouteRoutingModule],
  providers: [
    FormBuilder,
    NzMessageService,
  ],
  declarations: [],
})
export class RoutesModule {
}
