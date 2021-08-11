import { FormGroup } from '@angular/forms';

export function fromForm(obj: { [index: string]: any }, form: FormGroup, keys: string[]): void {
  for (const key of keys) {
    obj[key] = form.controls[key].value;
  }
}

export function toForm(obj: { [index: string]: any }, form: FormGroup, keys: string[]): void {
  const values: { [index: string]: any } = {};
  for (const key of keys) {
    values[key] = obj[key];
  }
  form.setValue(values);
}

export function compareForm(obj: { [index: string]: any }, form: FormGroup, keys: string[]): boolean {
  for (const key of keys) {
    if (obj[key] !== form.controls[key].value) {
      return false;
    }
  }
  return true;
}

export function compareAndAssign(obj: any, src: any): boolean {
  let changed = false;
  for (let key of Object.keys(src)) {
    const value = src[key];
    changed ||= (obj[key] != value);
    obj[key] = value;
  }
  return changed;
}
