import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class TimezoneInterceptor implements HttpInterceptor {
  timeZone = new Date();

  constructor() {
  }

  intercept(req: HttpRequest<{}>, next: HttpHandler): Observable<HttpEvent<{}>> {
    const modified = req.clone({
      setHeaders: {
        'x-timezone': Intl.DateTimeFormat().resolvedOptions().timeZone
      }
    });

    return next.handle(modified);
  }
}