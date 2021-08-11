import { Location } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Const, HttpService } from '@core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { UserProto } from '@proto';
import { User, UserStatus } from '../core/user.model';
import { UserService } from '../core/user.service';
import UserDataRequest = UserProto.UserDataRequest;
import UserDataResponse = UserProto.UserDataResponse;
import UserChangeStatusRequest = UserProto.UserChangeStatusRequest;

@Component({
  selector: 'app-user-detail',
  styleUrls: ['./user.detail.component.less'],
  templateUrl: './user.detail.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UserDetailComponent implements OnInit {
  submitting = false;
  loading = true;
  user: User | null = null;
  status = UserStatus.ACTIVE;
  statuses = UserStatus.values.slice(1);
  dateFormat = Const.ANGULAR_DATE_FORMAT;

  constructor(
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef,
    public userService: UserService,
    private httpService: HttpService,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
  ) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => this.loadData(params.id));
  }

  loadData(userId: string): void {
    this.loading = true;
    this.httpService
      .request(UserDataRequest.create({ userId }), UserDataResponse)
      .subscribe((res) => {
        this.loading = false;
        this.user = this.userService.pbToUser(res.user);
        this.status = this.user.status;
        this.cdr.detectChanges();
      });
  }

  get disabled(): boolean {
    return this.user?.status === this.status;
  }

  changeStatus(status?: UserStatus): void {
    if (this.user === null) {
      return;
    }
    this.submitting = true;
    status && (this.status = status);
    this.httpService
      .request(
        UserChangeStatusRequest.create({
          userId: this.user.id,
          status: this.status.code,
        }),
      )
      .subscribe(() => {
        this.user && (this.user.status = this.status);
        this.submitting = false;
        this.cancel();
      });
  }

  submit(): void {
  }

  cancel(): void {
    this.location.back();
  }
}
