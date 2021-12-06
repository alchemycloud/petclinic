import {environment} from '../../../environments/environment';
import {TraceService} from '../traceService.service';
import {PetType, UserRole} from './enums';
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

  myVisits(request: MyVisitsRequest): Observable<Array<MyVisitsResponse>> {
    let params: HttpParams = new HttpParams();
    if (request.userIdId !== null) {
      params = params.set('userIdId', request.userIdId.toString());
    }

    return this.http.get<Array<MyVisitsResponse>>(
      environment.backendUrl + '/visit/my-visits', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<Array<MyVisitsResponse>>('myVisits'))
    );
  }

  scheduledVisits(): Observable<Array<ScheduledVisitsResponse>> {
    return this.http.get<Array<ScheduledVisitsResponse>>(
      environment.backendUrl + '/visit/scheduled', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<ScheduledVisitsResponse>>('scheduledVisits'))
    );
  }

  readVisit(request: ReadVisitRequest): Observable<ReadVisitResponse> {
    return this.http.get<ReadVisitResponse>(
      environment.backendUrl + '/visit/visit/${request.id}', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<ReadVisitResponse>('readVisit'))
    );
  }

  updateVisit(request: UpdateVisitRequest): Observable<UpdateVisitResponse> {
    return this.http.put<UpdateVisitResponse>(
      environment.backendUrl + '/visit/visit/${request.id}', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<UpdateVisitResponse>('updateVisit'))
    );
  }

  deleteVisit(request: DeleteVisitRequest): Observable<Record<string, never>> {
    return this.http.delete<Record<string, never>>(
      environment.backendUrl + '/visit/visit/${request.id}', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Record<string, never>>('deleteVisit'))
    );
  }

  vetVisits(request: VetVisitsRequest): Observable<PagedDto<VetVisitsResponse>> {
    let params: HttpParams = new HttpParams();
    if (request.userId !== null) {
      params = params.set('userId', request.userId.toString());
    }
    if (request.drop !== null) {
      params = params.set('drop', request.drop.toString());
    }
    if (request.take !== null) {
      params = params.set('take', request.take.toString());
    }

    return this.http.get<PagedDto<VetVisitsResponse>>(
      environment.backendUrl + '/visit/vet-visits', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<PagedDto<VetVisitsResponse>>('vetVisits'))
    );
  }

  createVisit(request: CreateVisitRequest): Observable<CreateVisitResponse> {
    return this.http.post<CreateVisitResponse>(
      environment.backendUrl + '/visit/visit', request, {
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

export class ScheduledVisitsResponse {
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

export class MyVisitsResponse {
  id: number;
  vetId: number;
  petId: number;
  visitNumber: number;
  timestamp: Date;
  petWeight: number;
  description: string;
  scheduled: boolean;
  petOwnerId: number;
  petName: string;
  petBirthdate: Date;
  petPetType: PetType;
  petVaccinated: boolean;
  ownerId: number;
  ownerUserId: number;
  ownerAddress: string;
  ownerCity: string;
  ownerTelephone: string;
  userId: number;
  userFirstName: string;
  userLastName: string;
  userBirthdate: Date;
  userActive: boolean;
  userRole: UserRole;
  userEmail: string;
  userPasswordHash: string;
  userEmailVerificationCode: string;
  userEmailVerificationCodeTimestamp: Date;
  userEmailVerified: boolean;
  userResetPasswordCode: string;
  userResetPasswordCodeTimestamp: Date;

  constructor(id: number,
              vetId: number,
              petId: number,
              visitNumber: number,
              timestamp: Date,
              petWeight: number,
              description: string,
              scheduled: boolean,
              petOwnerId: number,
              petName: string,
              petBirthdate: Date,
              petPetType: PetType,
              petVaccinated: boolean,
              ownerId: number,
              ownerUserId: number,
              ownerAddress: string,
              ownerCity: string,
              ownerTelephone: string,
              userId: number,
              userFirstName: string,
              userLastName: string,
              userBirthdate: Date,
              userActive: boolean,
              userRole: UserRole,
              userEmail: string,
              userPasswordHash: string,
              userEmailVerificationCode: string,
              userEmailVerificationCodeTimestamp: Date,
              userEmailVerified: boolean,
              userResetPasswordCode: string,
              userResetPasswordCodeTimestamp: Date) {
    this.id = id;
    this.vetId = vetId;
    this.petId = petId;
    this.visitNumber = visitNumber;
    this.timestamp = timestamp;
    this.petWeight = petWeight;
    this.description = description;
    this.scheduled = scheduled;
    this.petOwnerId = petOwnerId;
    this.petName = petName;
    this.petBirthdate = petBirthdate;
    this.petPetType = petPetType;
    this.petVaccinated = petVaccinated;
    this.ownerId = ownerId;
    this.ownerUserId = ownerUserId;
    this.ownerAddress = ownerAddress;
    this.ownerCity = ownerCity;
    this.ownerTelephone = ownerTelephone;
    this.userId = userId;
    this.userFirstName = userFirstName;
    this.userLastName = userLastName;
    this.userBirthdate = userBirthdate;
    this.userActive = userActive;
    this.userRole = userRole;
    this.userEmail = userEmail;
    this.userPasswordHash = userPasswordHash;
    this.userEmailVerificationCode = userEmailVerificationCode;
    this.userEmailVerificationCodeTimestamp = userEmailVerificationCodeTimestamp;
    this.userEmailVerified = userEmailVerified;
    this.userResetPasswordCode = userResetPasswordCode;
    this.userResetPasswordCodeTimestamp = userResetPasswordCodeTimestamp;
  }

}

export class VetVisitsResponse {
  vetUserId: number;
  petName: string;
  visitNumber: number;
  scheduled: boolean;

  constructor(vetUserId: number,
              petName: string,
              visitNumber: number,
              scheduled: boolean) {
    this.vetUserId = vetUserId;
    this.petName = petName;
    this.visitNumber = visitNumber;
    this.scheduled = scheduled;
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

export class MyVisitsRequest {
  userIdId: number;

  constructor(userIdId: number) {
    this.userIdId = userIdId;
  }

}

export class VetVisitsRequest {
  userId: number;
  drop: number;
  take: number;

  constructor(userId: number,
              drop: number,
              take: number) {
    this.userId = userId;
    this.drop = drop;
    this.take = take;
  }

}
