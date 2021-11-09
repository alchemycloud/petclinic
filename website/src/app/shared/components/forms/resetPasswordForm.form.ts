import {AuthenticationApiService, ResetPasswordRequest} from '../../../services/backend/authenticationApi.service';
import {SessionService} from '../../../services/session.service';
import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {debounceTime, tap} from 'rxjs/operators';

export class ResetPasswordFormModel {
  resetPasswordCode: string;
  newPassword: string;

  constructor(resetPasswordCode: string,
              newPassword: string) {
    this.resetPasswordCode = resetPasswordCode;
    this.newPassword = newPassword;
  }

}

@Component({
  selector: 'reset-password-form',
  templateUrl: './resetPasswordForm.form.html',
  styleUrls: ['./resetPasswordForm.form.scss']
})
export class ResetPasswordForm implements OnInit, AfterViewInit {
  @Input() resetPasswordCode: string;
  model: ResetPasswordFormModel = new ResetPasswordFormModel('', '');
  submitDisabled = false;
  formGroup: FormGroup;
  resetPasswordCodeControl: FormControl;
  newPasswordControl: FormControl;

  constructor(private readonly authenticationApi: AuthenticationApiService, private readonly sessionService: SessionService,
              private readonly router: Router, private readonly fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.init();
    this.formGroup = this.fb.group({
      'resetPasswordCode': new FormControl(this.model.resetPasswordCode, [
        Validators.required,
        Validators.minLength(64),
        Validators.maxLength(64)], []),
      'newPassword': new FormControl(this.model.newPassword, [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(255),
        Validators.pattern(/^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z*&@%+/_'!#$^?:.(\\)\\[\\]{}~\\-]{8,}$/)], [])
    });
    this.resetPasswordCodeControl = this.formGroup.get('resetPasswordCode') as FormControl;
    this.newPasswordControl = this.formGroup.get('newPassword') as FormControl;

    this.formGroup.statusChanges.pipe(tap(() => {
      // fill the model with new data
      this.model.resetPasswordCode = this.resetPasswordCodeControl.value;
      this.model.newPassword = this.newPasswordControl.value;
    }), debounceTime(675)).subscribe((newStatus) => {

    });

  }

  ngAfterViewInit(): void {

  }


  init() {

  }

  submit() {
    this.submitDisabled = true;
    this.authenticationApi.resetPassword(new ResetPasswordRequest(this.model.resetPasswordCode,
      this.model.newPassword))
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

