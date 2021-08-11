import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { Observable } from 'rxjs';
import { WebpbMessage, WebpbMeta } from 'webpb';
import { map } from 'rxjs/operators';

const urljoin = require('url-join');

@Injectable()
export class HttpService {
  private httpHeaders: HttpHeaders = new HttpHeaders();

  constructor(private http: HttpClient) {
    this.httpHeaders.set('Content-Type', 'application/json');
  }

  request<R extends WebpbMessage>(message: WebpbMessage, response?: { prototype: R }): Observable<R> {
    const meta = message.webpbMeta();
    const url = this.formatUrl(meta)
    return this.doRequest(url, meta.method, message.toWebpbAlias())
      .pipe(map(res => response ? (response as any)['fromAlias'](res) : res));
  }

  formatUrl(meta: WebpbMeta): string {
    const baseUrl = environment.api.baseUrl ?? '';
    const context = meta.context ?? '';
    const path = meta.path ?? '';
    return urljoin(baseUrl, context, path);
  }

  doRequest(url: string, method: string, body?: any): Observable<any> {
    switch (method) {
      case 'GET':
        return this.http.get(url);

      case 'POST':
        return this.http.post(url, body, { headers: this.httpHeaders });

      case 'DELETE':
        return this.http.delete(url);

      case 'PUT':
        return this.http.put(url, body, { headers: this.httpHeaders });

      case 'PATCH':
        return this.http.patch(url, body, { headers: this.httpHeaders });
    }
    throw new Error(`Unhandled ${method}`);
  }
}
