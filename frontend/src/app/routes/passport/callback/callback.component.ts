import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@core';
import { mergeMap } from 'rxjs/operators';
import { EMPTY } from 'rxjs';

@Component({
  selector: 'app-passport-callback',
  template: ``,
})
export class PassportCallbackComponent implements OnInit {
  type = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    this.route.queryParams
      .pipe(
        mergeMap((params) => {
          if (params.type === 'login') {
            this.authService.redirectLogin();
            return EMPTY;
          } else if (params.type === 'authorize') {
            this.authService.redirectAuthorize();
            return EMPTY;
          } else if (params.code) {
            return this.authService.getToken(params.code);
          } else {
            throw params;
          }
        })
      )
      .subscribe({
        next: () => this.router.navigateByUrl('/'),
        error: () => this.router.navigateByUrl('/exception/403')
      });
  }
}
