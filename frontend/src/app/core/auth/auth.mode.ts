export class AuthedUser {
  id = '';
  nickname = '';
  avatar = '';
}

export class Oauth2Token {
  accessToken: string = '';
  expiresIn: number = 0;
  refreshToken: string = '';
  tokenType: string = '';
  scope: string = '';

  constructor(private time = Date.now()) {
  }

  isExpired(): boolean {
    return (this.time + (1000 * this.expiresIn) - Date.now()) < 5000;
  }
}
