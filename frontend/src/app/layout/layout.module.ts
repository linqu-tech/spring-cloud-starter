import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { IconsProviderModule } from '../icons-provider.module';
import { LayoutBasicComponent } from './basic/basic.component';
import { LayoutPassportComponent } from './passport/passport.component';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { HeaderUserComponent } from './basic/widgets/user.component';

@NgModule({
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    CommonModule,
    FormsModule,
    HttpClientModule,
    IconsProviderModule,
    NzAvatarModule,
    NzDropDownModule,
    NzLayoutModule,
    NzMenuModule,
    RouterModule,
    RouterModule,
  ],
  declarations: [LayoutBasicComponent, LayoutPassportComponent, HeaderUserComponent],
  exports: [LayoutBasicComponent, LayoutPassportComponent],
})
export class LayoutModule {}
