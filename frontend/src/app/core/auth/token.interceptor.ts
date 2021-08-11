import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CTX_ANONYMOUS } from '@core';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.context.get(CTX_ANONYMOUS)) {
      return next.handle(req);
    }
    return this.authService.getToken()
      .pipe(
        mergeMap(token => {
          req = req.clone({
            url: req.url,
            setHeaders: {
              Authorization: `${token.tokenType} ${token.accessToken}`
            }
          });
          return next.handle(req);
        })
      );
  }
}
