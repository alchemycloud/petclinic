import {environment} from '../../../environments/environment';
import {TraceService} from '../traceService.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable()
export class FileApiService {
  private readonly applicationJson = 'application/json';
  private readonly bearer = 'Bearer ';

  private header() {
    return new HttpHeaders({
      'Content-Type': this.applicationJson
    });
  }


  constructor(private readonly http: HttpClient, private readonly traceService: TraceService) {

  }

  findFile(request: FindFileRequest): Observable<{}> {
    return this.http.get<{}>(
      environment.backendUrl + '/file/${request.key}/${request.fileName}', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<{}>('findFile'))
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

export class FileUploadDTO {
  fileName: string;
  base64: string;

  constructor(fileName: string,
              base64: string) {
    this.fileName = fileName;
    this.base64 = base64;
  }

}

export class FileDTO {
  path: string;

  constructor(path: string) {
    this.path = path;
  }

}

export class FindFileRequest {
  key: string;
  fileName: string;

  constructor(key: string,
              fileName: string) {
    this.key = key;
    this.fileName = fileName;
  }

}
