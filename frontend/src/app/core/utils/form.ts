import {
  AbstractControlOptions,
  AsyncValidatorFn,
  FormBuilder,
  FormGroup,
  ValidatorFn
} from '@angular/forms';

export interface ControlConfig {
  name: string;
  value: any;
  validatorOrOpts?: ValidatorFn | ValidatorFn[] | AbstractControlOptions | null;
  asyncValidator?: AsyncValidatorFn | AsyncValidatorFn[] | null;
}

export class FormMapper {
  form: FormGroup;
  changed = false;

  constructor(private formBuilder: FormBuilder, private configs: ControlConfig[]) {
    const controlsConfig: { [key: string]: any } = {};
    for (const config of configs) {
      controlsConfig[config.name]
        = formBuilder.control(config.value, config.validatorOrOpts, config.asyncValidator);
    }
    this.form = formBuilder.group(controlsConfig);
    this.form.valueChanges.subscribe(() => (this.changed = this.isChanged()));
  }

  readForm<T>(params: T = {} as T): T {
    for (const config of this.configs) {
      const control = this.form.get(config.name);
      control && ((params as { [key: string]: any })[config.name] = control.value);
    }
    return params;
  }

  resetDefault() {
    for (const config of this.configs) {
      this.setValue(config.name, config.value);
    }
  }

  reset() {
    this.form.reset();
  }

  setValue(property: string, value: any) {
    const control = this.form.get(property);
    control?.setValue(value);
  }

  updateForm(obj: any, asDefault = false): void {
    for (const config of this.configs) {
      const value = obj[config.name];
      if (value !== undefined) {
        this.setValue(config.name, value);
        asDefault && (config.value = value);
      }
    }
    this.changed = this.isChanged();
  }

  isChanged(): boolean {
    for (const config of this.configs) {
      const control = this.form.get(config.name);
      if (control?.value != config.value) {
        return true;
      }
    }
    return false;
  }
}
