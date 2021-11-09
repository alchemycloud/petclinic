import {ForgotPasswordForm} from '../forms/forgotPasswordForm.form';
import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

export class ForgotPasswordPageModel {


  constructor() {

  }

}

@Component({
  selector: 'forgot-password-page',
  templateUrl: './forgotPasswordPage.page.html',
  styleUrls: ['./forgotPasswordPage.page.scss']
})
export class ForgotPasswordPage implements OnInit, AfterViewInit {
  @ViewChild(ForgotPasswordForm)
  private readonly forgotPasswordFormElement: ForgotPasswordForm;
  model: ForgotPasswordPageModel = new ForgotPasswordPageModel();

  constructor(private readonly route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

}

