import {AppMaterialModule} from '../app-material.module';
import {AutoFocusDirective} from '../directive/autoFocus.directive';
import {AuthenticationApiService} from '../services/backend/authenticationApi.service';
import {FileApiService} from '../services/backend/fileApi.service';
import {OwnerApiService} from '../services/backend/ownerApi.service';
import {PetApiService} from '../services/backend/petApi.service';
import {UserApiService} from '../services/backend/userApi.service';
import {VetApiService} from '../services/backend/vetApi.service';
import {VisitApiService} from '../services/backend/visitApi.service';
import {SessionService} from '../services/session.service';
import {ChangePasswordForm} from './components/forms/changePasswordForm.form';
import {ForgotPasswordForm, ForgotPasswordFormConfirm} from './components/forms/forgotPasswordForm.form';
import {ResetPasswordForm} from './components/forms/resetPasswordForm.form';
import {SignInForm} from './components/forms/signInForm.form';
import {SignUpForm} from './components/forms/signUpForm.form';
import {VerifyEmailForm} from './components/forms/verifyEmailForm.form';
import {ForgotPasswordPage} from './components/pages/forgotPasswordPage.page';
import {ResetPasswordPage} from './components/pages/resetPasswordPage.page';
import {SignInPage} from './components/pages/signInPage.page';
import {SignUpPage} from './components/pages/signUpPage.page';
import {VerifyEmailPage} from './components/pages/verifyEmailPage.page';
import {SharedRoutingModule} from './shared-routing.module';
import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    AutoFocusDirective,
    SignInPage,
    SignUpPage,
    VerifyEmailPage,
    ForgotPasswordPage,
    ResetPasswordPage,
    ChangePasswordForm,
    SignInForm,
    SignUpForm,
    VerifyEmailForm,
    ForgotPasswordForm,
    ForgotPasswordFormConfirm,
    ResetPasswordForm
  ],
  imports: [
    CommonModule,
    AppMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    SharedRoutingModule
  ],
  providers: [
    SessionService,
    VetApiService,
    VisitApiService,
    AuthenticationApiService,
    PetApiService,
    UserApiService,
    OwnerApiService,
    FileApiService
  ],
  exports: [
    AutoFocusDirective,
    SignInPage,
    SignUpPage,
    VerifyEmailPage,
    ForgotPasswordPage,
    ResetPasswordPage,
    ChangePasswordForm,
    SignInForm,
    SignUpForm,
    VerifyEmailForm,
    ForgotPasswordForm,
    ForgotPasswordFormConfirm,
    ResetPasswordForm
  ]
})
export class Shared {
}
