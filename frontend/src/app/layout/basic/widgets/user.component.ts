import { ChangeDetectionStrategy, Component } from '@angular/core';
import { AuthedUser, AuthService } from '@core';

@Component({
  selector: 'header-user',
  template: `
    <div class="alain-default__nav-item d-flex align-items-center px-sm" nz-dropdown nzPlacement="bottomRight" [nzDropdownMenu]="userMenu">
      <nz-avatar [nzSrc]="user.avatar" nzSize="default" class="mr-sm"></nz-avatar>
      {{ user.nickname }}
    </div>
    <nz-dropdown-menu #userMenu="nzDropdownMenu">
      <div nz-menu class="width-sm">
        <div nz-menu-item (click)="logout()">
          <i nz-icon nzType="logout" class="mr-sm"></i>
          Logout
        </div>
      </div>
    </nz-dropdown-menu>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderUserComponent {

  constructor(private authService: AuthService) {
  }

  get user(): AuthedUser {
    return this.authService.user;
  }

  logout(): void {
    this.authService.logout(true);
  }
}
