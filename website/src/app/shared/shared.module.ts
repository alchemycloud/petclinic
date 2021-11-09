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
import {Footer} from './components/containers/footer.container';
import {PrivateHeader} from './components/containers/privateHeader.container';
import {PublicHeader} from './components/containers/publicHeader.container';
import {PetTypeDropDown} from './components/dropdowns/petTypeDropDown.dropdown';
import {ChangePasswordForm} from './components/forms/changePasswordForm.form';
import {ForgotPasswordForm, ForgotPasswordFormConfirm} from './components/forms/forgotPasswordForm.form';
import {PetCreateForm} from './components/forms/petCreateForm.form';
import {PetUpdateForm} from './components/forms/petUpdateForm.form';
import {ResetPasswordForm} from './components/forms/resetPasswordForm.form';
import {SignInForm} from './components/forms/signInForm.form';
import {SignUpForm} from './components/forms/signUpForm.form';
import {VerifyEmailForm} from './components/forms/verifyEmailForm.form';
import {PetsList} from './components/lists/petsList.list';
import {AboutPage} from './components/pages/aboutPage.page';
import {ForgotPasswordPage} from './components/pages/forgotPasswordPage.page';
import {PetCreatePage} from './components/pages/petCreatePage.page';
import {PetUpdatePage} from './components/pages/petUpdatePage.page';
import {PetsPage} from './components/pages/petsPage.page';
import {ResetPasswordPage} from './components/pages/resetPasswordPage.page';
import {SignInPage} from './components/pages/signInPage.page';
import {SignUpPage} from './components/pages/signUpPage.page';
import {VerifyEmailPage} from './components/pages/verifyEmailPage.page';
import {WelcomePage} from './components/pages/welcomePage.page';
import {SharedRoutingModule} from './shared-routing.module';
import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    AutoFocusDirective,
    PublicHeader,
    PrivateHeader,
    Footer,
    WelcomePage,
    AboutPage,
    PetsPage,
    PetCreatePage,
    PetUpdateForm,
    PetUpdatePage,
    SignInPage,
    SignUpPage,
    VerifyEmailPage,
    ForgotPasswordPage,
    ResetPasswordPage,
    ChangePasswordForm,
    PetsList,
    PetCreateForm,
    PetTypeDropDown,
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
    PublicHeader,
    PrivateHeader,
    Footer,
    WelcomePage,
    AboutPage,
    PetsPage,
    PetCreatePage,
    PetUpdateForm,
    PetUpdatePage,
    SignInPage,
    SignUpPage,
    VerifyEmailPage,
    ForgotPasswordPage,
    ResetPasswordPage,
    ChangePasswordForm,
    PetsList,
    PetCreateForm,
    PetTypeDropDown,
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
