import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ForgotPasswordPage} from './components/pages/forgotPasswordPage.page';
import {ResetPasswordPage} from './components/pages/resetPasswordPage.page';
import {SignInPage} from './components/pages/signInPage.page';
import {SignUpPage} from './components/pages/signUpPage.page';
import {VerifyEmailPage} from './components/pages/verifyEmailPage.page';

const routes: Routes = [
  {path: 'sign-in', component: SignInPage},
  {path: 'sign-up', component: SignUpPage},
  {path: 'verify-email/:emailVerificationCode', component: VerifyEmailPage},
  {path: 'forgot-password', component: ForgotPasswordPage},
  {path: 'reset-password/:resetPasswordCode', component: ResetPasswordPage}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SharedRoutingModule {
}
