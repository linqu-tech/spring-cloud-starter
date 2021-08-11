export interface Environment {
  production: boolean;
  useHash: boolean;
  redirectUrl: string;
  oauth2Url: string;
  api: ApiConfig;
}

export interface ApiConfig {
  baseUrl: string;
}
