import { Pageable } from '@core';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { Params } from '@angular/router';

interface ObjectKeys {
  [key: string]: boolean | number | string;
}

export interface IQueryPrams extends ObjectKeys {
  page: number;
  size: number;
  sort: string;
}

export class QueryParams {
  page = 1;
  size = 10;
  sort = '';
  filter = '';

  static create(pageable: Pageable, filter: string): QueryParams {
    const params = new QueryParams();
    Object.assign(params, pageable);
    params.filter = filter;
    return params;
  }

  updateByTableQuery(params: NzTableQueryParams): void {
    this.page = params.pageIndex;
    this.size = params.pageSize;
    this.sort = params.sort
      .filter(e => e.value === 'ascend' || e.value === 'descend')
      .map(e => `${e.key},${e.value === 'ascend' ? 'asc' : 'desc'}`)
      .join(';');
  }

  getSortOrder(key: string): 'ascend' | 'descend' | null {
    if (!this.sort) {
      return null;
    }
    const orders = this.sort.split(";")
      .filter(sort => sort.startsWith(`${key},`))
      .map(sort => sort.split(",")[1] === 'asc' ? 'ascend' : 'descend');
    return orders[0] || null;
  }

  toParams(): Params {
    const params: Params = {};
    for (let entry of Object.entries(this)) {
      const value = entry[1];
      if (value !== undefined && value !== null && value !== '') {
        params[entry[0]] = value;
      }
    }
    console.log(params);
    return params;
  }
}
