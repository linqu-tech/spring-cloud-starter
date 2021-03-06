import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';

import { ExceptionRoutingModule } from './exception-routing.module';

import { Exception403Component } from './403.component';
import { Exception404Component } from './404.component';
import { Exception500Component } from './500.component';
import { CoreExceptionModule } from '@core';

const COMPONENTS = [Exception403Component, Exception404Component, Exception500Component];

@NgModule({
  imports: [CommonModule, CoreExceptionModule, NzButtonModule, NzCardModule, ExceptionRoutingModule],
  declarations: [...COMPONENTS],
})
export class ExceptionModule {
}
