import {Observable, throwError as observableThrowError} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {SessionService} from './session.service';
import {AuthenticationApiService, RefreshTokenRequest} from './backend/authenticationApi.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Router} from '@angular/router';
import {catchError, flatMap, map} from 'rxjs/operators';
import {environment} from '../../environments/environment';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private static urlWhitelist = [
    environment.backendUrl + '/refresh-token',
    environment.backendUrl + '/sign-in',
    environment.backendUrl + '/forgot-password'
  ];

  constructor(
    private readonly sessionService: SessionService,
    private readonly authenticationApi: AuthenticationApiService,
    private readonly jwtHelper: JwtHelperService,
    private readonly router: Router,
  ) {

  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const sessionData = this.sessionService.getSessionData();
    const refreshToken = sessionData == null ? null : this.sessionService.getRefreshToken();
    const accessToken = sessionData == null ? null : this.sessionService.getAccessToken();

    if (AuthInterceptor.isWhitelisted(req.url)) {
      return next.handle(req);
    } else if (this.isValid(accessToken)) {
      return next.handle(AuthInterceptor.requestWithToken(req, accessToken));
    } else {
      return this.refreshAccessTokenWith(refreshToken).pipe(
        flatMap((newAccessToken) => {
          return next.handle(AuthInterceptor.requestWithToken(req, newAccessToken));
        })
      );
    }
  }

  private static isWhitelisted(url: string) {
    return AuthInterceptor.urlWhitelist.includes(url);
  }

  private static requestWithToken(req, token) {
    if (token) {
      return req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
          'Access-Control-Allow-Origin': '*'
        }
      });
    } else {
      return req.clone({
        setHeaders: {
          'Access-Control-Allow-Origin': '*'
        }
      });
    }
  }

  private isValid(token) {
    let result = false;

    try {
      result = (token != null && !this.jwtHelper.isTokenExpired(token));
    } catch (error) {
      result = false;
    }

    return result;
  }

  private refreshAccessTokenWith(refreshToken) {
    if (this.isValid(refreshToken)) {
      return this.freshAccessToken(refreshToken);
    } else {
      return this.backToSignIn();
    }
  }

  private freshAccessToken(refreshToken) {
    return this.authenticationApi.refreshToken(new RefreshTokenRequest(refreshToken))
      .pipe(
        map((response) => {
          return response.accessToken;
        }),
        catchError(() => {
          return this.backToSignIn();
        })
      );
  }

  private backToSignIn() {
    this.sessionService.clear();
    this.router.navigate(['/sign-in']);

    return observableThrowError('Token missing or expired, returning to sign in page');
  }
}
