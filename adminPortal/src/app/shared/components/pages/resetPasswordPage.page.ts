import {ResetPasswordForm} from '../forms/resetPasswordForm.form';
import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';

export class ResetPasswordPageModel {


  constructor() {

  }

}

@Component({
  selector: 'reset-password-page',
  templateUrl: './resetPasswordPage.page.html',
  styleUrls: ['./resetPasswordPage.page.scss']
})
export class ResetPasswordPage implements OnInit, AfterViewInit {
  @Input() resetPasswordCode: string = null;
  @ViewChild(ResetPasswordForm)
  private readonly resetPasswordFormElement: ResetPasswordForm;
  model: ResetPasswordPageModel = new ResetPasswordPageModel();

  constructor(private readonly route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.resetPasswordCode = params.resetPasswordCode;
    });
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

}

