import { Environment } from '@env/index';

export const environment = {
  production: false,
  useHash: false,
  redirectUrl: 'http://127.0.0.1:4200/passport/callback',
  oauth2Url: 'http://127.0.0.1:8080',
  api: {
    baseUrl: './',
    refreshTokenEnabled: true,
    refreshTokenType: 'auth-refresh',
  }
} as Environment;
