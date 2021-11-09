import {AuthenticationApiService, ForgotPasswordRequest} from '../../../services/backend/authenticationApi.service';
import {AfterViewInit, Component, Inject, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog';
import {debounceTime, tap} from 'rxjs/operators';

export class ForgotPasswordFormModel {
  email: string;

  constructor(email: string) {
    this.email = email;
  }

}

@Component({
  selector: 'forgot-password-form',
  templateUrl: './forgotPasswordForm.form.html',
  styleUrls: ['./forgotPasswordForm.form.scss']
})
export class ForgotPasswordForm implements OnInit, AfterViewInit {
  @Input() model: ForgotPasswordFormModel;
  submitDisabled = false;
  formGroup: FormGroup;
  emailControl: FormControl;

  constructor(private readonly authenticationApi: AuthenticationApiService, private readonly dialog: MatDialog, private readonly fb: FormBuilder) {
    if (this.model == null) {
      this.model = new ForgotPasswordFormModel('');
    }
  }

  ngOnInit(): void {
    this.init();
    this.formGroup = this.fb.group({
      'email': new FormControl(this.model.email, [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(128),
        Validators.email], [])
    });
    this.emailControl = this.formGroup.get('email') as FormControl;

    this.formGroup.statusChanges.pipe(tap(() => {
      // fill the model with new data
      this.model.email = this.emailControl.value;
    }), debounceTime(675)).subscribe((newStatus) => {

    });

  }

  ngAfterViewInit(): void {

  }


  init() {

  }

  submit() {
    this.submitDisabled = true;
    this.authenticationApi.forgotPassword(new ForgotPasswordRequest(this.model.email))
      .subscribe(() => {
        this.submitDisabled = false;
        const dialogRef = this.dialog.open(ForgotPasswordFormConfirm, {
          panelClass: 'c-dialog',
          width: '550px'
        });
        dialogRef.afterClosed().subscribe(result => {

        });
      }, (_) => {
        this.submitDisabled = false;
      });
  }

  onSubmitClick() {
    this.submit();
  }

}
@Component({
  selector: 'ForgotPasswordFormConfirm',
  template:
    `<button mat-dialog-close
             mat-icon-button
             type="button"
             class="c-dialog__close"  data-qa="close">
    <mat-icon fontSet="fal"
              fontIcon="fa-times"></mat-icon>
    </button>
    <h1 mat-dialog-title
        class="c-dialog__header">Warning</h1>
    <mat-dialog-content class="c-dialog__content">
      <p>Are you sure you want to do this?</p>
    </mat-dialog-content>
    <mat-dialog-actions class="c-dialog__actions"
                        align="end">
      <button color="primary" mat-button type="button" mat-dialog-close data-qa="cancel">CANCEL</button>

    </mat-dialog-actions>`,
})
export class ForgotPasswordFormConfirm {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {

  }

}
