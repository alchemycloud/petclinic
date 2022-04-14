import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from '../services/authGuard.service';
import {AboutPage} from './components/pages/aboutPage.page';
import {PetCreatePage} from './components/pages/petCreatePage.page';
import {PetUpdatePage} from './components/pages/petUpdatePage.page';
import {PetsPage} from './components/pages/petsPage.page';
import {WelcomePage} from './components/pages/welcomePage.page';

const routes: Routes = [
  {path: 'welcome-page', component: WelcomePage},
  {path: 'about', component: AboutPage},
  {path: ':tenant/private/pets', canActivate: [AuthGuard], component: PetsPage},
  {path: ':tenant/private/pet/new', canActivate: [AuthGuard], component: PetCreatePage},
  {path: ':tenant/private/pet/:id', canActivate: [AuthGuard], component: PetUpdatePage}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SharedRoutingModule {
}
