import { Component, OnInit } from '@angular/core';
import { compareAndAssign, Const, FormMapper, HttpService, Pageable, QueryParams } from '@core';
import { UserProto } from '@proto';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { User, UserGender, UserStatus } from '../core/user.model';
import { UserService } from '../core/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { NzTableFilterList } from 'ng-zorro-antd/table/src/table.types';
import UserListRequest = UserProto.UserListRequest;
import UserListResponse = UserProto.UserListResponse;
import UserDeleteRequest = UserProto.UserDeleteRequest;

class PageParams extends QueryParams {
  genders: string | undefined;
  nickname: string | undefined;
  status: number | undefined;
  userId: string | undefined;

  getGenders(): number[] {
    return this.genders ? this.genders.split(",").map(e => Number(e)) : [];
  }

  updateByTableQuery(params: NzTableQueryParams) {
    super.updateByTableQuery(params);
    this.genders = params.filter.map(e => e.value).join(',');
  }
}

@Component({
  selector: 'app-user-list',
  styleUrls: ['./user.list.component.less'],
  templateUrl: './user.list.component.html',
})
export class UserListComponent implements OnInit {
  data: User[] = [];
  formMapper: FormMapper;
  genders: NzTableFilterList = UserGender.values.map(e => ({ text: e.name, value: e.code }));
  loading = false;
  params: PageParams = new PageParams();
  statuses = UserStatus.values;
  total = 1;

  constructor(
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private httpService: HttpService,
    private router: Router,
    private userService: UserService,
  ) {
    this.formMapper = new FormMapper(formBuilder, [
      { name: 'nickname', value: null },
      { name: 'status', value: UserStatus.ALL.code },
      { name: 'userId', value: null, validatorOrOpts: [Const.VALIDATOR_NUMBER] },
    ]);
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((res) => {
      if (!res && Object.keys(res).length === 0 || compareAndAssign(this.params, res)) {
        this.params.status && (this.params.status = Number(this.params.status));
        this.updateQueryParams();
        this.formMapper.updateForm(this.params);
        this.loadData();
      }
    });
  }

  loadData(): void {
    this.loading = true;
    this.httpService
      .request(
        UserListRequest.create({
          genders: this.params.getGenders(),
          nickname: this.params.nickname,
          pageable: Pageable.fromQueryParams(this.params),
          status: this.params.status,
          userId: this.params.userId,
        }),
        UserListResponse
      )
      .subscribe({
        next: (res) => {
          const paging = res.paging;
          if (paging.totalPage > 0 && this.params.page > paging.totalPage) {
            this.params.page = paging.totalPage;
            this.updateQueryParams();
            this.loadData();
            return;
          }
          const users: User[] = [];
          for (const pb of res.users) {
            users.push(this.userService.pbToUser(pb));
          }
          this.data = users;
          this.params.page = paging.page;
          this.params.size = paging.size;
          this.total = paging.totalCount;
          this.loading = false;
        },
        error: () => this.loading = false
      });
  }

  sortOrder(key: string): 'ascend' | 'descend' | null {
    return this.params.getSortOrder(key);
  }

  updateQueryParams(): void {
    const genders = this.params.getGenders();
    this.genders.forEach(e => e.byDefault = genders.indexOf(e.value) >= 0);
    this.router.navigate([], {
      relativeTo: this.activatedRoute,
      queryParams: { ...this.params.toParams() },
      replaceUrl: true,
    });
  }

  onQueryParamsChanged(queryParams: NzTableQueryParams): void {
    const params = new PageParams();
    params.updateByTableQuery(queryParams);
    if (compareAndAssign(this.params, params)) {
      this.updateQueryParams();
      this.loadData();
    }
  }

  detail(id: string): void {
    this.router.navigate(['../detail'], {
      relativeTo: this.activatedRoute,
      queryParams: { id },
    });
  }

  delete(id: string): void {
    this.httpService
      .request(UserDeleteRequest.create({ userId: id }))
      .subscribe(() => this.loadData());
  }

  submit(): void {
    this.formMapper.readForm(this.params);
    this.updateQueryParams();
    this.loadData();
  }

  reset() {
    this.formMapper.resetDefault();
    this.formMapper.readForm(this.params);
    this.updateQueryParams();
    this.loadData();
  }
}
