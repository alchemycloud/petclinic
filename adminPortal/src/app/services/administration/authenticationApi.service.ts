import {environment} from '../../../environments/environment';
import {SessionData} from '../session.service';
import {TraceService} from '../traceService.service';
import {TenantAccessLevel, UserRole} from './enums';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError, map, share} from 'rxjs/operators';

@Injectable()
export class AuthenticationApiService {
  private readonly applicationJson = 'application/json';
  private readonly bearer = 'Bearer ';

  private header() {
    return new HttpHeaders({
      'content-type': this.applicationJson
    });
  }


  refreshTokenCall;

  constructor(private readonly http: HttpClient, private readonly traceService: TraceService) {

  }

  resetPassword(request: ResetPasswordRequest): Observable<Record<string, never>> {
    return this.http.post<Record<string, never>>(
      environment.administrationUrl + '/reset-password', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Record<string, never>>('resetPassword'))
    );
  }

  forgotPassword(request: ForgotPasswordRequest): Observable<Record<string, never>> {
    return this.http.post<Record<string, never>>(
      environment.administrationUrl + '/forgot-password', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Record<string, never>>('forgotPassword'))
    );
  }

  signIn(request: SignInRequest): Observable<SignInResponse> {
    return this.http.post<SignInResponse>(
      environment.administrationUrl + '/sign-in', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<SignInResponse>('signIn'))
    );
  }

  verifyEmail(request: VerifyEmailRequest): Observable<Record<string, never>> {
    return this.http.post<Record<string, never>>(
      environment.administrationUrl + '/verify-email', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Record<string, never>>('verifyEmail'))
    );
  }

  signUp(request: SignUpRequest): Observable<Record<string, never>> {
    return this.http.post<Record<string, never>>(
      environment.administrationUrl + '/sign-up', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Record<string, never>>('signUp'))
    );
  }

  refreshToken(request: RefreshTokenRequest): Observable<SignInResponse> {
    if (!this.refreshTokenCall) {
      this.refreshTokenCall = this.http.post<SignInResponse>(environment.administrationUrl + '/refresh-token', request, {
        headers: this.header()
      }).pipe(
        catchError(this.handleError<SignInResponse>('refreshToken')),
        map((response) => {
          this.sessionService.save(new SessionData(response.accessToken, response.refreshToken, response.tokens, response.email, response.role,
            response.firstName, response.lastName, response.tenants));
          this.refreshTokenCall = null;
          return response;
        }),
        // we're making the observable hot, so that only one refresh token happens at the time.
        // check https://stackoverflow.com/questions/52874725/in-rxjs-why-does-a-pipe-get-executed-once-for-each-subscription for details
        share()
      );
    }

    return this.refreshTokenCall;
  }


  changePassword(request: ChangePasswordRequest): Observable<Record<string, never>> {
    return this.http.post<Record<string, never>>(
      environment.administrationUrl + '/change-password', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Record<string, never>>('changePassword'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      this.traceService.trace('apiError.' + operation + '.' + error.error).subscribe();

      // rethrow the error so that callers may react to it
      return observableThrowError(error);
    };
  }

}

export class ForgotPasswordRequest {
  email: string;

  constructor(email: string) {
    this.email = email;
  }

}

export class RefreshTokenRequest {
  refreshToken: string;

  constructor(refreshToken: string) {
    this.refreshToken = refreshToken;
  }

}

export class ResetPasswordRequest {
  resetPasswordCode: string;
  newPassword: string;

  constructor(resetPasswordCode: string,
              newPassword: string) {
    this.resetPasswordCode = resetPasswordCode;
    this.newPassword = newPassword;
  }

}

export class SignUpRequest {
  firstName: string;
  lastName: string;
  birthday: Date;
  active: boolean;
  email: string;
  password: string;

  constructor(firstName: string,
              lastName: string,
              birthday: Date,
              active: boolean,
              email: string,
              password: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.active = active;
    this.email = email;
    this.password = password;
  }

}

export class SignInResponseTenants {
  identifier: string;
  name: string;
  accessLevel: TenantAccessLevel;

  constructor(identifier: string,
              name: string,
              accessLevel: TenantAccessLevel) {
    this.identifier = identifier;
    this.name = name;
    this.accessLevel = accessLevel;
  }

}

export class SignInRequest {
  email: string;
  password: string;

  constructor(email: string,
              password: string) {
    this.email = email;
    this.password = password;
  }

}

export class SignInResponse {
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

export class SignInResponseTokens {
  tenant: string;
  accessToken: string;
  refreshToken: string;

  constructor(tenant: string,
              accessToken: string,
              refreshToken: string) {
    this.tenant = tenant;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

}

export class ChangePasswordRequest {
  oldPassword: string;
  newPassword: string;

  constructor(oldPassword: string,
              newPassword: string) {
    this.oldPassword = oldPassword;
    this.newPassword = newPassword;
  }

}

export class VerifyEmailRequest {
  emailVerificationCode: string;

  constructor(emailVerificationCode: string) {
    this.emailVerificationCode = emailVerificationCode;
  }

}
