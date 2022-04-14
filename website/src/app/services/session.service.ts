import {UserRole} from './administration/enums';
import {SignInResponseTenants, SignInResponseTokens} from './authenticationApi.service';
import {Injectable} from '@angular/core';

@Injectable()
export class SessionService {

  constructor() {
  }

  private static activeTenant: string;

  setActiveTenant(tenant: string) {
    SessionService.activeTenant = tenant;
  }

  getActiveTenant() {
    return SessionService.activeTenant;
  }

  public getAccessToken() {
    if (SessionService.activeTenant === null) {
      return this.getSessionData().accessToken;
    }
    return this.getSessionData().tokens.find(token => token.tenant === SessionService.activeTenant).accessToken;
  }

  public getRefreshToken() {
    if (SessionService.activeTenant === null) {
      return this.getSessionData().refreshToken;
    }
    return this.getSessionData().tokens.find(token => token.tenant === SessionService.activeTenant).refreshToken;
  }

  public save(sessionData: SessionData) {
    localStorage.setItem('accessToken', sessionData.accessToken);
    localStorage.setItem('refreshToken', sessionData.refreshToken);
    localStorage.setItem('tokens', JSON.stringify(sessionData.tokens));
    localStorage.setItem('email', sessionData.email);
    localStorage.setItem('role', UserRole[sessionData.role]);
    localStorage.setItem('firstName', sessionData.firstName);
    localStorage.setItem('lastName', sessionData.lastName);
    localStorage.setItem('tenants', JSON.stringify(sessionData.tenants));
  }

  public clear() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('tokens');
    localStorage.removeItem('email');
    localStorage.removeItem('role');
    localStorage.removeItem('firstName');
    localStorage.removeItem('lastName');
    localStorage.removeItem('tenants');
  }

  public getSessionData(): SessionData {
    if (localStorage.getItem('accessToken') !== null) {
      return new SessionData(
        localStorage.getItem('accessToken'),
        localStorage.getItem('refreshToken'),
        JSON.parse(localStorage.getItem('tokens') || '[]'),
        localStorage.getItem('email'),
        UserRole[localStorage.getItem('role')],
        localStorage.getItem('firstName'),
        localStorage.getItem('lastName'),
        JSON.parse(localStorage.getItem('tenants') || '[]')
      );
    } else {
      return null;
    }
  }

  public isLoggedIn() {
    return localStorage.getItem('accessToken') !== null;
  }

}

export class SessionData {
  accessToken: string;
  refreshToken: string;
  tokens: Array<SignInResponseTokens>;
  email: string;
  role: UserRole;
  firstName: string;
  lastName: string;
  tenants: Array<SignInResponseTenants>;

  constructor(accessToken: string,
              refreshToken: string,
              tokens: Array<SignInResponseTokens>,
              email: string,
              role: UserRole,
              firstName: string,
              lastName: string,
              tenants: Array<SignInResponseTenants>) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.tokens = tokens;
    this.email = email;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
    this.tenants = tenants;
  }

}
