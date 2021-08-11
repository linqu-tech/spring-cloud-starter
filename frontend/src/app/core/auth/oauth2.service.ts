import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Oauth2Proto } from '@proto';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as Webpb from 'webpb';
import { CTX_ANONYMOUS } from '../model';
import { HttpService } from '../service';
import { Oauth2Token } from './auth.mode';
import { v4 as uuid } from 'uuid';
import GrantType = Oauth2Proto.GrantType;
import Oauth2AuthorizeRequest = Oauth2Proto.Oauth2AuthorizeRequest;
import Oauth2LogoutRequest = Oauth2Proto.Oauth2LogoutRequest;
import Oauth2TokenRequest = Oauth2Proto.Oauth2TokenRequest;
import Oauth2TokenResponse = Oauth2Proto.Oauth2TokenResponse;
import ResponseType = Oauth2Proto.ResponseType;
import { environment } from '@env/environment';

@Injectable()
export class Oauth2Service {
  private static CLIENT_ID = 'browser';

  private static SCOPE = 'ui';

  constructor(private http: HttpClient,
              private httpService: HttpService) {
  }

  redirectAuthorize(): void {
    const meta = Oauth2AuthorizeRequest.create({
      type: ResponseType.CODE,
      clientId: Oauth2Service.CLIENT_ID,
      scope: Oauth2Service.SCOPE,
      redirectUri: `${environment.redirectUrl}`,
      state: uuid()
    }).webpbMeta();
    window.location.href = `${environment.oauth2Url}${meta.context}${meta.path}`;
  }

  redirectLogin() {
    const redirect = encodeURIComponent(`${environment.redirectUrl}?type=`);
    window.location.href = `${environment.oauth2Url}/login.html?redirect=${redirect}authorize`;
  }

  logout(): void {
    const meta = Oauth2LogoutRequest.create().webpbMeta();
    const redirect = encodeURIComponent(`${environment.redirectUrl}?type=`);
    window.location.href = `${environment.oauth2Url}${meta.context}${meta.path}?redirect=${redirect}login`;
  }

  getToken(code: string): Observable<Oauth2Token> {
    const message = Oauth2TokenRequest.create({
      grantType: GrantType.AUTHORIZATION_CODE,
      clientId: Oauth2Service.CLIENT_ID,
      redirectUri: `${environment.redirectUrl}`,
      code: code
    });
    return this.request(message, true);
  }

  refresh(refreshToken: string): Observable<Oauth2Token> {
    const message = Oauth2TokenRequest.create({
      grantType: GrantType.REFRESH_TOKEN,
      clientId: Oauth2Service.CLIENT_ID,
      refreshToken: refreshToken
    });
    return this.request(message, true);
  }

  private request(message: Oauth2TokenRequest, anonymous = false): Observable<Oauth2Token> {
    const url = this.httpService.formatUrl(message.webpbMeta());
    const body = Webpb.query('', message.toWebpbAlias());
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      Authorization: 'Basic ' + btoa(`${Oauth2Service.CLIENT_ID}:secret`),
    });
    return this.http
      .post(url, body, {
        headers: headers,
        context: new HttpContext().set(CTX_ANONYMOUS, anonymous)
      })
      .pipe(
        map((data: any) => {
          const res = Oauth2TokenResponse.fromAlias(data);
          return Object.assign(new Oauth2Token(), res);
        }),
      );
  }
}
