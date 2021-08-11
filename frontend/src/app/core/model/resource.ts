import { CommonProto } from '@proto';
import { QueryParams } from './query.params';
import IPagingPb = CommonProto.IPagingPb;
import PageablePb = CommonProto.PageablePb;

export interface Resource<T> {
  code: number;
  message: string;
  data: T;
}

export class Pageable {
  page = 1;
  size = 10;
  sort = '';
  pagination: boolean | undefined;

  toPb(): PageablePb {
    return PageablePb.create(this);
  }

  static fromQueryParams(params: QueryParams): Pageable {
    return Object.assign(new Pageable(), params);
  }
}

export class Paging implements IPagingPb {
  page = 0;
  size = 0;
  totalCount = 0;
  totalPage = 0;

  populate(paging: IPagingPb): boolean {
    if (paging) {
      this.page = paging.page;
      this.size = paging.size;
      this.totalCount = paging.totalCount;
      this.totalPage = paging.totalPage;
    }
    return true;
  }
}
