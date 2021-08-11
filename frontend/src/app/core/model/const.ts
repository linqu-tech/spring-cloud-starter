import { HttpContextToken } from '@angular/common/http';
import { Validators } from '@angular/forms';

export class Const {
  static OK = 0;

  static ASCEND = 'ascend';

  static DESCEND = 'descend';

  static DATE_FORMAT = 'yyyy/MM/dd HH:mm:ss';

  static ANGULAR_DATE_FORMAT = 'yyyy/MM/dd HH:mm:ss';

  static PREVIEW = '/assets/placeholder-image.png';

  static VALIDATOR_USERNAME = [Validators.required, Validators.minLength(3), Validators.maxLength(20)];

  static VALIDATOR_PASSWORD = [Validators.required, Validators.minLength(6), Validators.maxLength(20)];

  static VALIDATOR_NUMBER = Validators.pattern(/^\d{0,19}$/);
}

export const CTX_ANONYMOUS = new HttpContextToken<boolean>(() => false);
