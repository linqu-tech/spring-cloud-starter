import { Direction, Directionality } from '@angular/cdk/bidi';
import {
  ChangeDetectionStrategy,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  Optional,
  ViewChild,
  ViewEncapsulation,
} from '@angular/core';
import { DomSanitizer, SafeHtml, SafeUrl } from '@angular/platform-browser';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

export type ExceptionType = 403 | 404 | 500;

@Component({
  selector: 'exception',
  exportAs: 'exception',
  templateUrl: './exception.component.html',
  host: {
    '[class.exception]': 'true',
    '[class.exception-rtl]': `dir === 'rtl'`,
  },
  preserveWhitespaces: false,
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['exception.component.less']
})
export class ExceptionComponent implements OnInit, OnDestroy {
  static ngAcceptInputType_type: ExceptionType | string;

  private destroy$ = new Subject<void>();

  @ViewChild('contentTpl', { static: true })
  private contentTpl: ElementRef | null = null;

  locale: { [key: string]: string } = {
    'tips': 'Back To Homepage',
    '403': "Sorry, you don't have access to this page",
    '404': 'Sorry, the page you visited does not exist',
    '500': 'Sorry, the server is reporting an error'
  };
  hasContent = false;
  dir: Direction = 'ltr';

  _desc: SafeHtml = '';
  _img: SafeUrl = '';
  _title: SafeHtml = '';
  _type: ExceptionType | 0 = 0;

  @Input()
  set type(value: ExceptionType) {
    const item: { img: string; title: string } = {
      403: {
        img: '/assets/403.svg',
        title: '403',
      },
      404: {
        img: '/assets/404.svg',
        title: '404',
      },
      500: {
        img: '/assets/500.svg',
        title: '500',
      },
    }[value];

    if (!item) {
      return;
    }

    this.fixImg(item.img);
    this._type = value;
    this._title = item.title;
    this._desc = '';
  }

  private fixImg(src: string): void {
    this._img = this.dom.bypassSecurityTrustStyle(`url('${src}')`);
  }

  @Input()
  set img(value: string) {
    this.fixImg(value);
  }

  @Input()
  set title(value: string) {
    this._title = this.dom.bypassSecurityTrustHtml(value);
  }

  @Input()
  set desc(value: string) {
    this._desc = this.dom.bypassSecurityTrustHtml(value);
  }

  constructor(private dom: DomSanitizer, @Optional() private directionality: Directionality) {
  }

  ngOnInit(): void {
    this.dir = this.directionality.value;
    this.directionality.change?.pipe(takeUntil(this.destroy$)).subscribe((direction: Direction) => {
      this.dir = direction;
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
