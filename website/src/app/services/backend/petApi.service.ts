import {environment} from '../../../environments/environment';
import {TraceService} from '../traceService.service';
import {PetType} from './enums';
import {PagedDto} from './pagedDto';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable()
export class PetApiService {
  private readonly applicationJson = 'application/json';
  private readonly bearer = 'Bearer ';

  private header() {
    return new HttpHeaders({
      'Content-Type': this.applicationJson
    });
  }


  constructor(private readonly http: HttpClient, private readonly traceService: TraceService) {

  }

  pets(request: PetsRequest): Observable<PagedDto<PetsResponse>> {
    let params: HttpParams = new HttpParams();
    if (request.drop != null) {
      params = params.set('drop', request.drop.toString());
    }
    if (request.take != null) {
      params = params.set('take', request.take.toString());
    }

    return this.http.get<PagedDto<PetsResponse>>(
      environment.backendUrl + '/pets', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<PagedDto<PetsResponse>>('pets'))
    );
  }

  updatePet(request: UpdatePetRequest): Observable<UpdatePetResponse> {
    return this.http.put<UpdatePetResponse>(
      environment.backendUrl + '/pet/${request.id}', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<UpdatePetResponse>('updatePet'))
    );
  }

  findPetbyType(request: FindPetbyTypeRequest): Observable<Array<FindPetbyTypeResponse>> {
    let params: HttpParams = new HttpParams();
    if (request.petType != null) {
      params = params.set('petType', request.petType.toString());
    }

    return this.http.get<Array<FindPetbyTypeResponse>>(
      environment.backendUrl + '/find-petby-type', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<Array<FindPetbyTypeResponse>>('findPetbyType'))
    );
  }

  createPet(request: CreatePetRequest): Observable<CreatePetResponse> {
    return this.http.post<CreatePetResponse>(
      environment.backendUrl + '/pet', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<CreatePetResponse>('createPet'))
    );
  }

  deletePet(request: DeletePetRequest): Observable<{}> {
    return this.http.delete<{}>(
      environment.backendUrl + '/pet/${request.id}', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<{}>('deletePet'))
    );
  }

  readPet(request: ReadPetRequest): Observable<ReadPetResponse> {
    return this.http.get<ReadPetResponse>(
      environment.backendUrl + '/pet/${request.id}', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<ReadPetResponse>('readPet'))
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

export class PetsResponse {
  id: number;
  name: string;
  petType: PetType;
  userLastName: string;

  constructor(id: number,
              name: string,
              petType: PetType,
              userLastName: string) {
    this.id = id;
    this.name = name;
    this.petType = petType;
    this.userLastName = userLastName;
  }

}

export class ReadPetRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class DeletePetRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class UpdatePetResponse {
  id: number;
  ownerId: number;
  name: string;
  birthdate: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(id: number,
              ownerId: number,
              name: string,
              birthdate: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    this.birthdate = birthdate;
    this.petType = petType;
    this.vaccinated = vaccinated;
  }

}

export class FindPetbyTypeResponse {
  name: string;
  petType: PetType;

  constructor(name: string,
              petType: PetType) {
    this.name = name;
    this.petType = petType;
  }

}

export class UpdatePetRequest {
  id: number;
  ownerId: number;
  name: string;
  birthdate: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(id: number,
              ownerId: number,
              name: string,
              birthdate: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    this.birthdate = birthdate;
    this.petType = petType;
    this.vaccinated = vaccinated;
  }

}

export class CreatePetRequest {
  ownerId: number;
  name: string;
  birthdate: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(ownerId: number,
              name: string,
              birthdate: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.ownerId = ownerId;
    this.name = name;
    this.birthdate = birthdate;
    this.petType = petType;
    this.vaccinated = vaccinated;
  }

}

export class FindPetbyTypeRequest {
  petType: PetType;

  constructor(petType: PetType) {
    this.petType = petType;
  }

}

export class PetsRequest {
  drop: number;
  take: number;

  constructor(drop: number,
              take: number) {
    this.drop = drop;
    this.take = take;
  }

}

export class CreatePetResponse {
  id: number;
  ownerId: number;
  name: string;
  birthdate: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(id: number,
              ownerId: number,
              name: string,
              birthdate: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    this.birthdate = birthdate;
    this.petType = petType;
    this.vaccinated = vaccinated;
  }

}

export class ReadPetResponse {
  id: number;
  ownerId: number;
  name: string;
  birthdate: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(id: number,
              ownerId: number,
              name: string,
              birthdate: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    this.birthdate = birthdate;
    this.petType = petType;
    this.vaccinated = vaccinated;
  }

}
