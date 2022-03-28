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
      'content-type': this.applicationJson
    });
  }


  constructor(private readonly http: HttpClient, private readonly traceService: TraceService) {

  }

  pets(request: PetsRequest): Observable<PagedDto<PetsResponse>> {
    let params: HttpParams = new HttpParams();
    if (request.drop !== null) {
      params = params.set('drop', request.drop.toString());
    }
    if (request.take !== null) {
      params = params.set('take', request.take.toString());
    }

    return this.http.get<PagedDto<PetsResponse>>(
      environment.backendUrl + '/pets/pets', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<PagedDto<PetsResponse>>('pets'))
    );
  }

  petsByType(request: PetsByTypeRequest): Observable<Array<PetsByTypeResponse>> {
    let params: HttpParams = new HttpParams();
    if (request.petType !== null) {
      params = params.set('petType', request.petType.toString());
    }

    return this.http.get<Array<PetsByTypeResponse>>(
      environment.backendUrl + '/pets/pets-by-type', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<Array<PetsByTypeResponse>>('petsByType'))
    );
  }

  updatePet(request: UpdatePetRequest): Observable<UpdatePetResponse> {
    return this.http.put<UpdatePetResponse>(
      environment.backendUrl + '/pets/update-pet', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<UpdatePetResponse>('updatePet'))
    );
  }

  createPet(request: CreatePetRequest): Observable<CreatePetResponse> {
    return this.http.post<CreatePetResponse>(
      environment.backendUrl + '/pets/create-pet', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<CreatePetResponse>('createPet'))
    );
  }

  deletePet(request: DeletePetRequest): Observable<Record<string, never>> {
    let params: HttpParams = new HttpParams();
    if (request.id !== null) {
      params = params.set('id', request.id.toString());
    }

    return this.http.delete<Record<string, never>>(
      environment.backendUrl + '/pets/delete-pet', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<Record<string, never>>('deletePet'))
    );
  }

  readPet(request: ReadPetRequest): Observable<ReadPetResponse> {
    let params: HttpParams = new HttpParams();
    if (request.id !== null) {
      params = params.set('id', request.id.toString());
    }

    return this.http.get<ReadPetResponse>(
      environment.backendUrl + '/pets/read-pet', {
        headers: this.header(),
        params
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

export class PetsByTypeResponse {
  id: number;
  name: string;

  constructor(id: number,
              name: string) {
    this.id = id;
    this.name = name;
  }

}

export class PetsResponse {
  id: number;
  name: string;
  petType: PetType;
  ownerLastName: string;

  constructor(id: number,
              name: string,
              petType: PetType,
              ownerLastName: string) {
    this.id = id;
    this.name = name;
    this.petType = petType;
    this.ownerLastName = ownerLastName;
  }

}

export class ReadPetRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class PetsByTypeRequest {
  petType: PetType;

  constructor(petType: PetType) {
    this.petType = petType;
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
  birthday: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(id: number,
              ownerId: number,
              name: string,
              birthday: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    this.birthday = birthday;
    this.petType = petType;
    this.vaccinated = vaccinated;
  }

}

export class UpdatePetRequest {
  id: number;
  ownerId: number;
  name: string;
  birthday: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(id: number,
              ownerId: number,
              name: string,
              birthday: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    this.birthday = birthday;
    this.petType = petType;
    this.vaccinated = vaccinated;
  }

}

export class CreatePetRequest {
  ownerId: number;
  name: string;
  birthday: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(ownerId: number,
              name: string,
              birthday: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.ownerId = ownerId;
    this.name = name;
    this.birthday = birthday;
    this.petType = petType;
    this.vaccinated = vaccinated;
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
  birthday: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(id: number,
              ownerId: number,
              name: string,
              birthday: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    this.birthday = birthday;
    this.petType = petType;
    this.vaccinated = vaccinated;
  }

}

export class ReadPetResponse {
  id: number;
  ownerId: number;
  name: string;
  birthday: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(id: number,
              ownerId: number,
              name: string,
              birthday: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    this.birthday = birthday;
    this.petType = petType;
    this.vaccinated = vaccinated;
  }

}
