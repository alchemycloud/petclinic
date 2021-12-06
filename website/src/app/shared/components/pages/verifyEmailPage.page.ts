import {VerifyEmailForm} from '../forms/verifyEmailForm.form';
import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';

export class VerifyEmailPageModel {


  constructor() {

  }

}

@Component({
  selector: 'verify-email-page',
  templateUrl: './verifyEmailPage.page.html',
  styleUrls: ['./verifyEmailPage.page.scss']
})
export class VerifyEmailPage implements OnInit, AfterViewInit {
  @Input() emailVerificationCode: string = null;
  @ViewChild(VerifyEmailForm)
  private readonly verifyEmailFormElement: VerifyEmailForm;
  model: VerifyEmailPageModel = new VerifyEmailPageModel();

  constructor(private readonly route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.emailVerificationCode = params.emailVerificationCode;
    });
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

}

