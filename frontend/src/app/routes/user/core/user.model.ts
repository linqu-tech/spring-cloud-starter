export class UserGender {
  private static codeMap: { [key: number]: UserGender } = {};

  static values: UserGender[] = [];

  static MALE = new UserGender(1, 'Female');

  static FEMALE = new UserGender(2, 'Male');

  code: number;

  name: string;

  private constructor(code: number, name: string) {
    UserGender.codeMap[code] = this;
    UserGender.values.push(this);
    this.code = code;
    this.name = name;
  }

  static of(code: number): UserGender {
    return this.codeMap[code];
  }
}

export class UserStatus {
  private static codeMap: { [key: number]: UserStatus } = {};

  static values: UserStatus[] = [];

  static ALL = new UserStatus(-1, 'All');

  static ACTIVE = new UserStatus(0, 'Active');

  static SUSPENDED = new UserStatus(1, 'Suspended');

  static INACTIVE = new UserStatus(2, 'Inactive');

  code: number;

  name: string;

  private constructor(code: number, name: string) {
    UserStatus.codeMap[code] = this;
    UserStatus.values.push(this);
    this.code = code;
    this.name = name;
  }

  static of(code: number): UserStatus {
    return this.codeMap[code];
  }

  toString(): string {
    return this.code + '';
  }
}

export class User {
  id = '';
  username = '';
  nickname = '';
  avatar = '';
  status: UserStatus = UserStatus.ACTIVE;
  gender: UserGender = UserGender.MALE;
  created: Date | null = null;
}
