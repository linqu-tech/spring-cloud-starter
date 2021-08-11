import { Environment } from '@env/index';

export const environment = {
  production: true,
  useHash: false,
  redirectUrl: 'https://starter.linqu.tech/passport/callback',
  oauth2Url: 'https://starter-api.linqu.tech',
  api: {
    baseUrl: 'https://starter-api.linqu.tech',
    refreshTokenEnabled: true,
    refreshTokenType: 'auth-refresh',
  },
} as Environment;
