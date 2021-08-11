import { Injectable } from '@angular/core';
import { AuthedUser, HttpService, LocalStorageService, Oauth2Service, Oauth2Token } from '@core';
import { EMPTY, Observable, of, ReplaySubject } from 'rxjs';
import { catchError, defaultIfEmpty, map, mergeMap, tap } from 'rxjs/operators';
import { UserProto } from '@proto';
import UserCurrentRequest = UserProto.UserCurrentRequest;
import UserCurrentResponse = UserProto.UserCurrentResponse;

const TOKEN_KEY = 'OAUTH2';

@Injectable()
export class AuthService {
  user: AuthedUser = new AuthedUser();

  private token: Oauth2Token | null = null;

  private observable: Observable<Oauth2Token> | null = null;

  constructor(
    private httpService: HttpService,
    private localStorageService: LocalStorageService,
    private oauth2Service: Oauth2Service,
  ) {
    this.token || (this.token = this.readOauth2Token());
  }

  redirectLogin() {
    this.oauth2Service.redirectLogin();
  }

  redirectAuthorize() {
    this.oauth2Service.redirectAuthorize();
  }

  auth(): Observable<boolean> {
    return this.getToken()
      .pipe(
        mergeMap(() => this.getUser()),
        map(() => this.isAuthed()),
        catchError(() => of(false)),
        defaultIfEmpty(false),
      );
  }

  private isAuthed(): boolean {
    return !!this.token && !this.token.isExpired() && !!this.user.id;
  }

  getToken(code?: string): Observable<Oauth2Token> {
    if (this.token && !this.token.isExpired()) {
      return of(this.token);
    }
    if (!this.observable) {
      if (!this.token && !code) {
        this.logout();
        return EMPTY;
      }
      const subject = new ReplaySubject<Oauth2Token>();
      this.observable = subject.asObservable();
      const observable: Observable<Oauth2Token> = this.token
        ? this.oauth2Service.refresh(this.token.refreshToken)
        : this.oauth2Service.getToken(code as string);
      observable
        .pipe(
          catchError(() => {
            this.logout();
            return EMPTY;
          }),
          tap(res => {
            this.token = res;
            this.localStorageService.set(TOKEN_KEY, JSON.stringify(res));
          })
        )
        .subscribe(res => {
          subject.next(res);
          subject.complete();
          this.observable = null;
        });
    }
    return this.observable ? this.observable : of(new Oauth2Token());
  }

  getUser(): Observable<AuthedUser> {
    if (this.user.id) {
      return of(this.user);
    }
    return this.httpService
      .request(UserCurrentRequest.create(), UserCurrentResponse)
      .pipe(map(res => {
        const user = Object.assign(new AuthedUser(), res.user);
        if (!user.id) {
          this.logout();
        }
        this.user = user;
        return user;
      }));
  }

  logout(clear = false): void {
    this.token = null;
    this.user = new AuthedUser();
    this.localStorageService.remove(TOKEN_KEY);
    if (clear) {
      this.oauth2Service.logout();
    } else {
      this.redirectLogin();
    }
  }

  private readOauth2Token(): Oauth2Token | null {
    const data = this.localStorageService.get(TOKEN_KEY);
    try {
      return data ? Object.assign(new Oauth2Token(), JSON.parse(data)) : null;
    } catch (e) {
      console.error(e);
      return null;
    }
  }
}
