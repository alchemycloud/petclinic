import {environment} from '../../../environments/environment';
import {TraceService} from '../traceService.service';
import {PetType} from './enums';
import {PagedDto} from './pagedDto';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable()
export class VisitApiService {
  private readonly applicationJson = 'application/json';
  private readonly bearer = 'Bearer ';

  private header() {
    return new HttpHeaders({
      'content-type': this.applicationJson
    });
  }


  constructor(private readonly http: HttpClient, private readonly traceService: TraceService) {

  }

  visitsForOwner(request: VisitsForOwnerRequest): Observable<PagedDto<VisitDTO>> {
    let params: HttpParams = new HttpParams();
    if (request.drop !== null) {
      params = params.set('drop', request.drop.toString());
    }
    if (request.take !== null) {
      params = params.set('take', request.take.toString());
    }

    return this.http.get<PagedDto<VisitDTO>>(
      environment.backendUrl + '/visits/visits-for-owner', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<PagedDto<VisitDTO>>('visitsForOwner'))
    );
  }

  scheduledVisits(request: ScheduledVisitsRequest): Observable<PagedDto<VisitDTO>> {
    let params: HttpParams = new HttpParams();
    if (request.drop !== null) {
      params = params.set('drop', request.drop.toString());
    }
    if (request.take !== null) {
      params = params.set('take', request.take.toString());
    }

    return this.http.get<PagedDto<VisitDTO>>(
      environment.backendUrl + '/visits/scheduled-visits', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<PagedDto<VisitDTO>>('scheduledVisits'))
    );
  }

  readVisit(request: ReadVisitRequest): Observable<ReadVisitResponse> {
    let params: HttpParams = new HttpParams();
    if (request.id !== null) {
      params = params.set('id', request.id.toString());
    }

    return this.http.get<ReadVisitResponse>(
      environment.backendUrl + '/visits/read-visit', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<ReadVisitResponse>('readVisit'))
    );
  }

  visitsByVet(request: VisitsByVetRequest): Observable<PagedDto<VisitDTO>> {
    let params: HttpParams = new HttpParams();
    if (request.vetId !== null) {
      params = params.set('vetId', request.vetId.toString());
    }
    if (request.drop !== null) {
      params = params.set('drop', request.drop.toString());
    }
    if (request.take !== null) {
      params = params.set('take', request.take.toString());
    }

    return this.http.get<PagedDto<VisitDTO>>(
      environment.backendUrl + '/visits/visits-by-vet', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<PagedDto<VisitDTO>>('visitsByVet'))
    );
  }

  updateVisit(request: UpdateVisitRequest): Observable<UpdateVisitResponse> {
    return this.http.put<UpdateVisitResponse>(
      environment.backendUrl + '/visits/update-visit', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<UpdateVisitResponse>('updateVisit'))
    );
  }

  visitsByPet(request: VisitsByPetRequest): Observable<PagedDto<VisitDTO>> {
    let params: HttpParams = new HttpParams();
    if (request.petId !== null) {
      params = params.set('petId', request.petId.toString());
    }
    if (request.drop !== null) {
      params = params.set('drop', request.drop.toString());
    }
    if (request.take !== null) {
      params = params.set('take', request.take.toString());
    }

    return this.http.get<PagedDto<VisitDTO>>(
      environment.backendUrl + '/visits/visits-by-pet', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<PagedDto<VisitDTO>>('visitsByPet'))
    );
  }

  deleteVisit(request: DeleteVisitRequest): Observable<Record<string, never>> {
    let params: HttpParams = new HttpParams();
    if (request.id !== null) {
      params = params.set('id', request.id.toString());
    }

    return this.http.delete<Record<string, never>>(
      environment.backendUrl + '/visits/delete-visit', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<Record<string, never>>('deleteVisit'))
    );
  }

  createVisit(request: CreateVisitRequest): Observable<CreateVisitResponse> {
    return this.http.post<CreateVisitResponse>(
      environment.backendUrl + '/visits/create-visit', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<CreateVisitResponse>('createVisit'))
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

export class UpdateVisitResponse {
  id: number;
  vetId: number;
  petId: number;
  visitNumber: number;
  timestamp: Date;
  petWeight: number;
  description: string;
  scheduled: boolean;

  constructor(id: number,
              vetId: number,
              petId: number,
              visitNumber: number,
              timestamp: Date,
              petWeight: number,
              description: string,
              scheduled: boolean) {
    this.id = id;
    this.vetId = vetId;
    this.petId = petId;
    this.visitNumber = visitNumber;
    this.timestamp = timestamp;
    this.petWeight = petWeight;
    this.description = description;
    this.scheduled = scheduled;
  }

}

export class CreateVisitResponse {
  id: number;
  vetId: number;
  petId: number;
  visitNumber: number;
  timestamp: Date;
  petWeight: number;
  description: string;
  scheduled: boolean;

  constructor(id: number,
              vetId: number,
              petId: number,
              visitNumber: number,
              timestamp: Date,
              petWeight: number,
              description: string,
              scheduled: boolean) {
    this.id = id;
    this.vetId = vetId;
    this.petId = petId;
    this.visitNumber = visitNumber;
    this.timestamp = timestamp;
    this.petWeight = petWeight;
    this.description = description;
    this.scheduled = scheduled;
  }

}

export class DeleteVisitRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class ReadVisitRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class VisitsByVetRequest {
  vetId: number;
  drop: number;
  take: number;

  constructor(vetId: number,
              drop: number,
              take: number) {
    this.vetId = vetId;
    this.drop = drop;
    this.take = take;
  }

}

export class UpdateVisitRequest {
  id: number;
  vetId: number;
  petId: number;
  visitNumber: number;
  timestamp: Date;
  petWeight: number;
  description: string;
  scheduled: boolean;

  constructor(id: number,
              vetId: number,
              petId: number,
              visitNumber: number,
              timestamp: Date,
              petWeight: number,
              description: string,
              scheduled: boolean) {
    this.id = id;
    this.vetId = vetId;
    this.petId = petId;
    this.visitNumber = visitNumber;
    this.timestamp = timestamp;
    this.petWeight = petWeight;
    this.description = description;
    this.scheduled = scheduled;
  }

}

export class CreateVisitRequest {
  vetId: number;
  petId: number;
  visitNumber: number;
  timestamp: Date;
  petWeight: number;
  description: string;
  scheduled: boolean;

  constructor(vetId: number,
              petId: number,
              visitNumber: number,
              timestamp: Date,
              petWeight: number,
              description: string,
              scheduled: boolean) {
    this.vetId = vetId;
    this.petId = petId;
    this.visitNumber = visitNumber;
    this.timestamp = timestamp;
    this.petWeight = petWeight;
    this.description = description;
    this.scheduled = scheduled;
  }

}

export class VisitsByPetRequest {
  petId: number;
  drop: number;
  take: number;

  constructor(petId: number,
              drop: number,
              take: number) {
    this.petId = petId;
    this.drop = drop;
    this.take = take;
  }

}

export class ScheduledVisitsRequest {
  drop: number;
  take: number;

  constructor(drop: number,
              take: number) {
    this.drop = drop;
    this.take = take;
  }

}

export class ReadVisitResponse {
  id: number;
  vetId: number;
  petId: number;
  visitNumber: number;
  timestamp: Date;
  petWeight: number;
  description: string;
  scheduled: boolean;

  constructor(id: number,
              vetId: number,
              petId: number,
              visitNumber: number,
              timestamp: Date,
              petWeight: number,
              description: string,
              scheduled: boolean) {
    this.id = id;
    this.vetId = vetId;
    this.petId = petId;
    this.visitNumber = visitNumber;
    this.timestamp = timestamp;
    this.petWeight = petWeight;
    this.description = description;
    this.scheduled = scheduled;
  }

}

export class VisitDTO {
  visitNumber: number;
  description: string;
  scheduled: boolean;
  name: string;
  petType: PetType;
  firstName: string;
  lastName: string;

  constructor(visitNumber: number,
              description: string,
              scheduled: boolean,
              name: string,
              petType: PetType,
              firstName: string,
              lastName: string) {
    this.visitNumber = visitNumber;
    this.description = description;
    this.scheduled = scheduled;
    this.name = name;
    this.petType = petType;
    this.firstName = firstName;
    this.lastName = lastName;
  }

}

export class VisitsForOwnerRequest {
  drop: number;
  take: number;

  constructor(drop: number,
              take: number) {
    this.drop = drop;
    this.take = take;
  }

}
