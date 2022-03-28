import {environment} from '../../../environments/environment';
import {TraceService} from '../traceService.service';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable()
export class TenantApiService {
  private readonly applicationJson = 'application/json';
  private readonly bearer = 'Bearer ';

  private header() {
    return new HttpHeaders({
      'content-type': this.applicationJson
    });
  }


  constructor(private readonly http: HttpClient, private readonly traceService: TraceService) {

  }

  searchTenants(request: SearchTenantsRequest): Observable<Array<SearchTenantsResponse>> {
    let params: HttpParams = new HttpParams();
    if (request.name !== null) {
      params = params.set('name', request.name.toString());
    }

    return this.http.get<Array<SearchTenantsResponse>>(
      environment.administrationUrl + '/tenants/search-tenants', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<Array<SearchTenantsResponse>>('searchTenants'))
    );
  }

  updateTenant(request: UpdateTenantRequest): Observable<Record<string, never>> {
    return this.http.put<Record<string, never>>(
      environment.administrationUrl + '/tenants/update-tenant', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Record<string, never>>('updateTenant'))
    );
  }

  readTenant(request: ReadTenantRequest): Observable<ReadTenantResponse> {
    let params: HttpParams = new HttpParams();
    if (request.identifier !== null) {
      params = params.set('identifier', request.identifier.toString());
    }

    return this.http.get<ReadTenantResponse>(
      environment.administrationUrl + '/tenants/read-tenant', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<ReadTenantResponse>('readTenant'))
    );
  }

  createTenant(request: CreateTenantRequest): Observable<CreateTenantResponse> {
    return this.http.post<CreateTenantResponse>(
      environment.administrationUrl + '/tenants/create-tenant', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<CreateTenantResponse>('createTenant'))
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

export class CreateTenantRequest {
  identifier: string;
  name: string;

  constructor(identifier: string,
              name: string) {
    this.identifier = identifier;
    this.name = name;
  }

}

export class CreateTenantResponse {
  identifier: string;

  constructor(identifier: string) {
    this.identifier = identifier;
  }

}

export class ReadTenantResponse {
  id: number;
  name: string;
  identifier: string;

  constructor(id: number,
              name: string,
              identifier: string) {
    this.id = id;
    this.name = name;
    this.identifier = identifier;
  }

}

export class SearchTenantsRequest {
  name: string;

  constructor(name: string) {
    this.name = name;
  }

}

export class SearchTenantsResponse {
  id: number;
  name: string;
  identifier: string;

  constructor(id: number,
              name: string,
              identifier: string) {
    this.id = id;
    this.name = name;
    this.identifier = identifier;
  }

}

export class UpdateTenantRequest {
  name: string;

  constructor(name: string) {
    this.name = name;
  }

}

export class ReadTenantRequest {
  identifier: string;

  constructor(identifier: string) {
    this.identifier = identifier;
  }

}
