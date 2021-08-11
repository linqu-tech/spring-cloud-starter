import { Component } from '@angular/core';
import { AuthService } from '@core';

@Component({
  selector: 'passport-oauth2',
  templateUrl: './oauth2.component.html',
})
export class PassportOauth2Component {
  constructor(private authService: AuthService) {
    authService.redirectLogin();
  }
}
