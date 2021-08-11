import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  CanLoad,
  Route,
  RouterStateSnapshot,
  UrlSegment
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizeGuard implements CanActivate, CanActivateChild, CanLoad {
  constructor(private authService: AuthService) {
  }

  private authorized(): Observable<boolean> {
    return this.authService.auth();
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.authorized();
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.authorized();
  }

  canLoad(route: Route, segments: UrlSegment[]): Observable<boolean> {
    return this.authorized();
  }
}
