import { Const, Pageable, Paging } from '@core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { NzTableQueryParams } from 'ng-zorro-antd/table';

export abstract class AbstractTableComponent<T extends Pageable> {
  // loading = false;
  // paging = new Paging();
  //
  // protected constructor(public params: T) {
  // }
  //
  // abstract loadData(): void;
  //
  // updateUrl(router?: Router, route?: ActivatedRoute): void {
  //   if (router && route) {
  //     router.navigate([], {
  //       relativeTo: route,
  //       queryParams: { ...this.params },
  //       replaceUrl: true,
  //     });
  //   }
  // }
  //
  // stChange(e: STChange): void {
  //   if (e.type === 'filter') {
  //     this.loadData();
  //   } else if (e.type === 'pi' || e.type === 'ps') {
  //     this.params.populate(e);
  //     this.loadData();
  //   } else if (e.type === 'sort') {
  //     this.params.sort = AbstractTableComponent.toPageableSort(e.sort?.map);
  //     this.loadData();
  //   }
  // }
  //
  // private static toPageableSort(map: { [key: string]: string } | undefined): string {
  //   if (!map) {
  //     return '';
  //   }
  //   const sorts: string[] = [];
  //   for (let key in map) {
  //     const d = map[key];
  //     sorts.push(`${key},${d}`);
  //   }
  //   return sorts.join(';');
  // }
  //
  // private static toSortMap(columns: STColumn[], str: string): { [key: string]: string } {
  //   if (!str) {
  //     return {};
  //   }
  //   const parts = str.split(';');
  //   const sortMap: { [key: string]: string } = {};
  //   for (let part of parts) {
  //     const [key, re] = part.split(',');
  //     for (let column of columns) {
  //       const sort: STColumnSort = column.sort as STColumnSort;
  //       if (!sort || !sort.key) {
  //         continue;
  //       }
  //       if (sort.key == key) {
  //         if (sort.reName?.ascend == re) {
  //           sortMap[key] = Const.ASCEND;
  //         } else if (sort.reName?.descend == re) {
  //           sortMap[key] = Const.DESCEND;
  //         }
  //       }
  //     }
  //   }
  //   return sortMap;
  // }
  //
  // sortFromQuery(columns: STColumn[], params: Params, sortMap: { [key: string]: string }) {
  //   this.updateSort(columns, params as Pageable, sortMap);
  // }
  //
  // onQueryParamsChange(params: NzTableQueryParams): void {
  //   const { pageSize, pageIndex, sort, filter } = params;
  //   const currentSort = sort.find(item => item.value !== null);
  //   const sortField = (currentSort && currentSort.key) || null;
  //   const sortOrder = (currentSort && currentSort.value) || null;
  //   this.loadData(pageIndex, pageSize, sortField, sortOrder, filter);
  // }
  //
  // updateSort(columns: STColumn[], pageable: Pageable, sortMap: { [key: string]: string }) {
  //   sortMap = pageable.sort !== undefined ? AbstractTableComponent.toSortMap(columns, pageable.sort) : sortMap;
  //   const pageableSort: { [key: string]: string } = {};
  //   for (let column of columns) {
  //     const sort: STColumnSort | undefined = column.sort as STColumnSort;
  //     if (!sort || !sort.key) {
  //       continue;
  //     }
  //     const sortKey = sort.key;
  //     const re: 'ascend' | 'descend' | null = sortMap[sortKey] as any;
  //     if (re) {
  //       sort.default = re;
  //       const reName = re === 'ascend' ? sort.reName?.ascend : sort.reName?.descend;
  //       reName && (pageableSort[sortKey] = reName);
  //     }
  //   }
  //   this.params.sort = AbstractTableComponent.toPageableSort(pageableSort);
  // }
}
