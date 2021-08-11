import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponseBase,
} from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '@env/environment';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Observable, of, throwError } from 'rxjs';
import { catchError, mergeMap } from 'rxjs/operators';
import { AuthService } from '../auth';

const CODE_MESSAGE: { [key: number]: string } = {};

@Injectable()
export class ResponseInterceptor implements HttpInterceptor {
  constructor(private injector: Injector) {
  }

  private get notification(): NzNotificationService {
    return this.injector.get(NzNotificationService);
  }

  private goTo(url: string): void {
    setTimeout(() => this.injector.get(Router).navigateByUrl(url));
  }

  private checkStatus(ev: HttpResponseBase): void {
    if ((ev.status >= 200 && ev.status < 300) || ev.status === 401) {
      return;
    }

    const errorText = CODE_MESSAGE[ev.status] || ev.statusText;
    this.notification.error(`Request error ${ev.status}: ${ev.url}`, errorText);
  }

  private handleData(ev: HttpResponseBase): Observable<any> {
    this.checkStatus(ev);
    switch (ev.status) {
      case 401:
        this.injector.get(AuthService).logout();
        break;
      // case 403:
      case 404:
      case 500:
        this.goTo(`/exception/${ev.status}`);
        break;
      default:
        if (ev instanceof HttpErrorResponse) {
          console.warn(ev);
        }
        break;
    }
    return ev instanceof HttpErrorResponse ? throwError(() => ev) : of(ev);
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let url = req.url;
    if (!url.startsWith(environment.api.baseUrl)) {
      url = environment.api.baseUrl + url;
    }
    req = req.clone({ url });
    return next.handle(req).pipe(
      mergeMap((ev) => {
        if (ev instanceof HttpResponseBase) {
          return this.handleData(ev);
        }
        return of(ev);
      }),
      catchError((err: HttpErrorResponse) => this.handleData(err)),
    );
  }
}
