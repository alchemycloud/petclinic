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

export class RefreshTokenRequest {
  refreshToken: string;

  constructor(refreshToken: string) {
    this.refreshToken = refreshToken;
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
