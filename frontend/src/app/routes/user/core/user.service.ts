import { Injectable } from '@angular/core';
import { ModelProto } from '@proto';
import { Const } from '@core';
import { User, UserGender, UserStatus } from './user.model';
import IUserPb = ModelProto.IUserPb;

@Injectable()
export class UserService {
  pbToUser(pb: IUserPb): User {
    const user: User = Object.assign(new User(), pb);
    user.id = pb.id;
    user.nickname = pb.nickname || '';
    user.avatar = pb.avatar || Const.PREVIEW;
    user.status = UserStatus.of(pb.status);
    user.gender = UserGender.of(pb.gender);
    user.created = new Date(pb.created);
    return user;
  }
}
