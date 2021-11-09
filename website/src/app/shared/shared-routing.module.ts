import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from '../services/authGuard.service';
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

const routes: Routes = [
  {path: 'welcome-page', component: WelcomePage},
  {path: 'about', component: AboutPage},
  {path: 'private/pets', canActivate: [AuthGuard], component: PetsPage},
  {path: 'private/pet/new', canActivate: [AuthGuard], component: PetCreatePage},
  {path: 'private/pet/:id', canActivate: [AuthGuard], component: PetUpdatePage},
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
