import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule, Injectable} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {ActivatedRouteSnapshot, RouteReuseStrategy} from '@angular/router';
import {AppMaterialModule} from './app-material.module';
import {AuthInterceptor} from './services/authInterceptor.service';
import {VersionInterceptor, VersionChangedComponent} from './services/versionInterceptor.service';
import {JwtModule, JwtModuleOptions} from '@auth0/angular-jwt';
import {AuthenticationApiService} from './services/administration/authenticationApi.service';
import {AuthGuard} from './services/authGuard.service';
import {FileApiService} from './services/backend/fileApi.service';
import {OwnerApiService} from './services/backend/ownerApi.service';
import {PetApiService} from './services/backend/petApi.service';
import {VetApiService} from './services/backend/vetApi.service';
import {VisitApiService} from './services/backend/visitApi.service';
import {SessionService} from './services/session.service';
import {TimezoneInterceptor} from './services/timezoneInterceptor.service';


const JWT_Module_Options: JwtModuleOptions = {
  config: {
    tokenGetter: () => {
      return localStorage.getItem('accessToken');
    },
    whitelistedDomains: [environment.administrationUrl]
  }
};

@Injectable()
export class DontReuseRoutes implements RouteReuseStrategy {
  shouldDetach(route: ActivatedRouteSnapshot): boolean {
    return false;
  }

  store(route: ActivatedRouteSnapshot, handle: {}): void {

  }

  shouldAttach(route: ActivatedRouteSnapshot): boolean {
    return false;
  }

  retrieve(route: ActivatedRouteSnapshot): {} {
    return null;
  }

  shouldReuseRoute(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
    return false; // default is true if configuration of current and future route are the same
  }
}

@NgModule({
  declarations: [
    AppComponent,
    VersionChangedComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    AppMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    // we need this desipite handling our own access tokens
    // since it's impossible to use it without the interceptor,
    // Sometimes we only have access token after the request was launched
    // so we have to handle it in our authInterceptor as well.
    JwtModule.forRoot(JWT_Module_Options)
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: VersionInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: TimezoneInterceptor, multi: true},
    {provide: RouteReuseStrategy, useClass: DontReuseRoutes},
    SessionService,
    AuthGuard,
    AuthenticationApiService,
    VetApiService,
    VisitApiService,
    PetApiService,
    OwnerApiService,
    FileApiService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}

