import {Component, Inject, Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog';


@Injectable()
export class VersionInterceptor implements HttpInterceptor {
  serverVersion = '1.0.0';

  constructor(private readonly dialog: MatDialog) {
  }

  intercept(req: HttpRequest<{}>, next: HttpHandler ): Observable<HttpEvent<{}>> {
    const modified = req.clone({
      setHeaders: {
        'x-server-version': this.serverVersion
      }
    });

    return next.handle(modified).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 412) {
          const errorModal = document.querySelector('.js-version-mismatch');

          if (!errorModal) {
            const dialogRef = this.dialog.open(VersionChangedComponent, {
              panelClass: ['c-dialog', 'js-version-mismatch'],
              width: '560px',
              data: {
              },
              disableClose: true
            });

            dialogRef.afterClosed().subscribe(result => {
              if (result === 'yes') {
                // reload app
                location.reload(true);
              }
            });
          }
        }
        return throwError(error);
    }));
  }
}

@Component({
  selector: 'VersionChangedComponent',
  template:
    `<mat-dialog-content class="c-dialog__content">
      <svg class="c-dialog__icon" viewBox="0 0 32 32" width="88" height="88" fill="#2196f3">
         <path opacity="0.4" d="M0 28.778v-8.649c0-0.411 0.163-0.804 0.454-1.095s0.684-0.454 1.095-0.454h8.649c1.379 0 2.070 1.668 1.097 2.645l-2.694 2.694c2.006 1.885 4.657 2.929 7.41 2.919 4.994-0.005 9.31-3.428 10.502-8.184 0.041-0.168 0.138-0.318 0.274-0.425s0.304-0.165 0.478-0.165h3.697c0.114 0 0.226 0.025 0.329 0.073s0.194 0.118 0.267 0.206c0.073 0.087 0.125 0.19 0.154 0.299s0.033 0.225 0.012 0.337c-1.396 7.413-7.905 13.021-15.723 13.021-4.118 0.006-8.079-1.583-11.052-4.432l-2.303 2.303c-0.977 0.977-2.645 0.286-2.645-1.093z"></path>
         <path d="M0.278 13.021c1.395-7.413 7.904-13.021 15.723-13.021 4.118-0.005 8.079 1.583 11.052 4.432l2.303-2.303c0.975-0.975 2.645-0.284 2.645 1.097v8.645c0 0.411-0.163 0.805-0.454 1.095s-0.684 0.453-1.095 0.453h-8.649c-1.379 0-2.070-1.668-1.097-2.645l2.694-2.694c-2.005-1.885-4.657-2.929-7.409-2.919-4.997 0.005-9.312 3.431-10.503 8.184-0.041 0.168-0.138 0.318-0.274 0.425s-0.304 0.165-0.478 0.165h-3.697c-0.114 0-0.226-0.025-0.329-0.073s-0.194-0.118-0.266-0.206-0.125-0.19-0.154-0.299c-0.029-0.11-0.033-0.225-0.012-0.336z"></path>
      </svg>
      <p class="c-dialog__text"
         i18n="@@NEW_ALCHEMY_EXPERIENCE_RELOAD_PAGE">
        To improve your Alchemy experience, please reload the page.
      </p>
  </mat-dialog-content>
  <mat-dialog-actions class="c-dialog__actions"
                      align="end">
      <button mat-raised-button color="primary" type="button" data-qa="yes" [mat-dialog-close]="'yes'">RELOAD</button>
  </mat-dialog-actions>`,
})
export class VersionChangedComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {

  }

}
