import {
  AuthenticationApiService,
  SignInRequest,
  SignInResponse
} from '../../../services/backend/authenticationApi.service';
import {UserRole} from '../../../services/backend/enums';
import {SessionData, SessionService} from '../../../services/session.service';
import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {debounceTime, tap} from 'rxjs/operators';

export class SignInFormModel {
  email: string;
  password: string;

  constructor(email: string,
              password: string) {
    this.email = email;
    this.password = password;
  }

}

@Component({
  selector: 'sign-in-form',
  templateUrl: './signInForm.form.html',
  styleUrls: ['./signInForm.form.scss']
})
export class SignInForm implements OnInit, AfterViewInit {
  @Input() model: SignInFormModel = new SignInFormModel('', '');
  submitDisabled = false;
  formGroup: FormGroup;
  emailControl: FormControl;
  passwordControl: FormControl;

  constructor(private readonly authenticationApi: AuthenticationApiService, private readonly sessionService: SessionService,
              private readonly fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.init();
    this.formGroup = this.fb.group({
      email: new FormControl(this.model.email, [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(128),
        Validators.email], []),
      password: new FormControl(this.model.password, [
        Validators.required,
        Validators.minLength(12),
        Validators.maxLength(128)], [])
    });
    this.emailControl = this.formGroup.get('email') as FormControl;
    this.passwordControl = this.formGroup.get('password') as FormControl;

    this.formGroup.statusChanges.pipe(tap(() => {
      // fill the model with new data
      this.model.email = this.emailControl.value;
      this.model.password = this.passwordControl.value;
    }), debounceTime(675)).subscribe((newStatus) => {

    });

  }

  ngAfterViewInit(): void {

  }


  init() {

  }

  submit() {
    this.submitDisabled = true;
    this.authenticationApi.signIn(new SignInRequest(this.model.email,
      this.model.password))
      .subscribe((response: SignInResponse) => {
      this.sessionService.save(new SessionData(response.accessToken, response.refreshToken, response.id, response.firstName, response.lastName,
          response.birthdate, response.active, response.role, response.email));
        this.submitDisabled = false;

      }, (_) => {
        this.submitDisabled = false;
      });
  }

  onSubmitClick() {
    this.submit();
  }

}

