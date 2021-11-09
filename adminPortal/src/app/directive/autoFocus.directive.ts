import {AfterContentInit, Directive, ElementRef} from '@angular/core';

@Directive({
  selector: '[inputAutoFocus]'
})
export class AutoFocusDirective implements AfterContentInit {
  constructor(private elem: ElementRef) {}

  ngAfterContentInit(): void {
    setTimeout(() => {
      this.elem.nativeElement.focus();

    }, 400);
  }
}
