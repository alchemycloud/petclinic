import {environment} from '../../../environments/environment';
import {TraceService} from '../traceService.service';
import {FileDTO} from './fileApi.service';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable()
export class VetApiService {
  private readonly applicationJson = 'application/json';
  private readonly bearer = 'Bearer ';

  private header() {
    return new HttpHeaders({
      'content-type': this.applicationJson
    });
  }


  constructor(private readonly http: HttpClient, private readonly traceService: TraceService) {

  }

  createVet(request: CreateVetRequest): Observable<CreateVetResponse> {
    return this.http.post<CreateVetResponse>(
      environment.backendUrl + '/vet', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<CreateVetResponse>('createVet'))
    );
  }

  deleteVet(request: DeleteVetRequest): Observable<Record<string, never>> {
    return this.http.delete<Record<string, never>>(
      environment.backendUrl + '/vet/${request.id}', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Record<string, never>>('deleteVet'))
    );
  }

  vetsWithSpecialties(request: VetDTO): Observable<Array<VetWithSpecialtiesDTO>> {
    let params: HttpParams = new HttpParams();
    if (request.id !== null) {
      params = params.set('id', request.id.toString());
    }
    if (request.firstName !== null) {
      params = params.set('firstName', request.firstName.toString());
    }
    if (request.lastName !== null) {
      params = params.set('lastName', request.lastName.toString());
    }

    return this.http.get<Array<VetWithSpecialtiesDTO>>(
      environment.backendUrl + '/vets', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<Array<VetWithSpecialtiesDTO>>('vetsWithSpecialties'))
    );
  }

  readVet(request: ReadVetRequest): Observable<ReadVetResponse> {
    return this.http.get<ReadVetResponse>(
      environment.backendUrl + '/vet/${request.id}', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<ReadVetResponse>('readVet'))
    );
  }

  updateVet(request: UpdateVetRequest): Observable<UpdateVetResponse> {
    return this.http.put<UpdateVetResponse>(
      environment.backendUrl + '/vet/${request.id}', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<UpdateVetResponse>('updateVet'))
    );
  }

  vetInfo(request: VetInfoRequest): Observable<VetWithSpecialtiesDTO> {
    return this.http.get<VetWithSpecialtiesDTO>(
      environment.backendUrl + '/vet/info/${request.id}', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<VetWithSpecialtiesDTO>('vetInfo'))
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

export class UpdateVetResponse {
  id: number;
  userId: number;
  image: FileDTO;

  constructor(id: number,
              userId: number,
              image: FileDTO) {
    this.id = id;
    this.userId = userId;
    this.image = image;
  }

}

export class CreateVetResponse {
  id: number;
  userId: number;
  image: FileDTO;

  constructor(id: number,
              userId: number,
              image: FileDTO) {
    this.id = id;
    this.userId = userId;
    this.image = image;
  }

}

export class VetWithSpecialtiesDTO {
  firstName: string;
  lastName: string;
  specialties: Array<string>;

  constructor(firstName: string,
              lastName: string,
              specialties: Array<string>) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.specialties = specialties;
  }

}

export class DeleteVetRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class ReadVetResponse {
  id: number;
  userId: number;
  image: FileDTO;

  constructor(id: number,
              userId: number,
              image: FileDTO) {
    this.id = id;
    this.userId = userId;
    this.image = image;
  }

}

export class UpdateVetRequest {
  id: number;
  userId: number;
  image: FileDTO;

  constructor(id: number,
              userId: number,
              image: FileDTO) {
    this.id = id;
    this.userId = userId;
    this.image = image;
  }

}

export class VetInfoRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class CreateVetRequest {
  userId: number;
  image: FileDTO;

  constructor(userId: number,
              image: FileDTO) {
    this.userId = userId;
    this.image = image;
  }

}

export class ReadVetRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class VetDTO {
  id: number;
  firstName: string;
  lastName: string;

  constructor(id: number,
              firstName: string,
              lastName: string) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

}
