import {AppMaterialModule} from '../app-material.module';
import {AutoFocusDirective} from '../directive/autoFocus.directive';
import {AuthenticationApiService} from '../services/administration/authenticationApi.service';
import {FileApiService} from '../services/backend/fileApi.service';
import {OwnerApiService} from '../services/backend/ownerApi.service';
import {PetApiService} from '../services/backend/petApi.service';
import {VetApiService} from '../services/backend/vetApi.service';
import {VisitApiService} from '../services/backend/visitApi.service';
import {SessionService} from '../services/session.service';
import {Footer} from './components/containers/footer.container';
import {PrivateHeader} from './components/containers/privateHeader.container';
import {PetTypeDropDown} from './components/dropdowns/petTypeDropDown.dropdown';
import {PetCreateForm} from './components/forms/petCreateForm.form';
import {PetUpdateForm} from './components/forms/petUpdateForm.form';
import {PetsList} from './components/lists/petsList.list';
import {AboutPage} from './components/pages/aboutPage.page';
import {PetCreatePage} from './components/pages/petCreatePage.page';
import {PetUpdatePage} from './components/pages/petUpdatePage.page';
import {PetsPage} from './components/pages/petsPage.page';
import {WelcomePage} from './components/pages/welcomePage.page';
import {SharedRoutingModule} from './shared-routing.module';
import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    AutoFocusDirective,
    PrivateHeader,
    Footer,
    WelcomePage,
    AboutPage,
    PetsPage,
    PetCreatePage,
    PetUpdateForm,
    PetUpdatePage,
    PetsList,
    PetCreateForm,
    PetTypeDropDown
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
    AuthenticationApiService,
    VetApiService,
    VisitApiService,
    PetApiService,
    OwnerApiService,
    FileApiService
  ],
  exports: [
    AutoFocusDirective,
    PrivateHeader,
    Footer,
    WelcomePage,
    AboutPage,
    PetsPage,
    PetCreatePage,
    PetUpdateForm,
    PetUpdatePage,
    PetsList,
    PetCreateForm,
    PetTypeDropDown
  ]
})
export class Shared {
}
