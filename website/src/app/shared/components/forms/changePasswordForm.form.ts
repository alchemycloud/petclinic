import {AuthenticationApiService, ChangePasswordRequest} from '../../../services/backend/authenticationApi.service';
import {SessionService} from '../../../services/session.service';
import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {debounceTime, tap} from 'rxjs/operators';

export class ChangePasswordFormModel {
  oldPassword: string;
  newPassword: string;

  constructor(oldPassword: string,
              newPassword: string) {
    this.oldPassword = oldPassword;
    this.newPassword = newPassword;
  }

}

@Component({
  selector: 'change-password-form',
  templateUrl: './changePasswordForm.form.html',
  styleUrls: ['./changePasswordForm.form.scss']
})
export class ChangePasswordForm implements OnInit, AfterViewInit {
  @Input() model: ChangePasswordFormModel;
  submitDisabled = false;
  formGroup: FormGroup;
  oldPasswordControl: FormControl;
  newPasswordControl: FormControl;

  constructor(private readonly authenticationApi: AuthenticationApiService, private readonly sessionService: SessionService,
              private readonly router: Router, private readonly fb: FormBuilder) {
    if (this.model == null) {
      this.model = new ChangePasswordFormModel('', '');
    }
  }

  ngOnInit(): void {
    this.init();
    this.formGroup = this.fb.group({
      'oldPassword': new FormControl(this.model.oldPassword, [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(255),
        Validators.pattern(/^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z*&@%+/_'!#$^?:.(\\)\\[\\]{}~\\-]{8,}$/)], []),
      'newPassword': new FormControl(this.model.newPassword, [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(255),
        Validators.pattern(/^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z*&@%+/_'!#$^?:.(\\)\\[\\]{}~\\-]{8,}$/)], [])
    });
    this.oldPasswordControl = this.formGroup.get('oldPassword') as FormControl;
    this.newPasswordControl = this.formGroup.get('newPassword') as FormControl;

    this.formGroup.statusChanges.pipe(tap(() => {
      // fill the model with new data
      this.model.oldPassword = this.oldPasswordControl.value;
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
    this.authenticationApi.changePassword(new ChangePasswordRequest(this.model.oldPassword,
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

