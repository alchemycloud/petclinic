import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {SessionService} from './session.service';

@Injectable()
export class AuthGuard implements CanActivate {
  serverVersion = '1.0.0';

  constructor(private readonly sessionService: SessionService, private readonly router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean > | Promise<boolean> | boolean {
    const sessionData = this.sessionService.getSessionData();
    if ( sessionData ) {
      return true;
    } else {
      this.router.navigate(['/sign-in'], { queryParams: { prevRout: state.url } });
      return false;
    }
  }
}
