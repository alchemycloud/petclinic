import {AuthenticationApiService, VerifyEmailRequest} from '../../../services/backend/authenticationApi.service';
import {SessionService} from '../../../services/session.service';
import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {debounceTime, tap} from 'rxjs/operators';

export class VerifyEmailFormModel {
  emailVerificationCode: string;

  constructor(emailVerificationCode: string) {
    this.emailVerificationCode = emailVerificationCode;
  }

}

@Component({
  selector: 'verify-email-form',
  templateUrl: './verifyEmailForm.form.html',
  styleUrls: ['./verifyEmailForm.form.scss']
})
export class VerifyEmailForm implements OnInit, AfterViewInit {
  @Input() emailVerificationCode: string;
  model: VerifyEmailFormModel = new VerifyEmailFormModel('');
  submitDisabled = false;
  formGroup: FormGroup;
  emailVerificationCodeControl: FormControl;

  constructor(private readonly authenticationApi: AuthenticationApiService, private readonly sessionService: SessionService,
              private readonly router: Router, private readonly fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.init();
    this.formGroup = this.fb.group({
      'emailVerificationCode': new FormControl(this.model.emailVerificationCode, [
        Validators.required,
        Validators.minLength(64),
        Validators.maxLength(64)], [])
    });
    this.emailVerificationCodeControl = this.formGroup.get('emailVerificationCode') as FormControl;

    this.formGroup.statusChanges.pipe(tap(() => {
      // fill the model with new data
      this.model.emailVerificationCode = this.emailVerificationCodeControl.value;
    }), debounceTime(675)).subscribe((newStatus) => {

    });

  }

  ngAfterViewInit(): void {

  }


  init() {

  }

  submit() {
    this.submitDisabled = true;
    this.authenticationApi.verifyEmail(new VerifyEmailRequest(this.model.emailVerificationCode))
      .subscribe(() => {
        this.submitDisabled = false;
        this.sessionService.clear();
        this.router.navigate(['/sign-in']);
      }, (_) => {
        this.submitDisabled = false;
      });
  }

  onSubmitClick() {
    this.submit();
  }

}

