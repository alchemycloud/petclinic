import {environment} from '../../../environments/environment';
import {TraceService} from '../traceService.service';
import {PetType, UserRole} from './enums';
import {PagedDto} from './pagedDto';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable()
export class OwnerApiService {
  private readonly applicationJson = 'application/json';
  private readonly bearer = 'Bearer ';

  private header() {
    return new HttpHeaders({
      'content-type': this.applicationJson
    });
  }


  constructor(private readonly http: HttpClient, private readonly traceService: TraceService) {

  }

  ownersWithPets(): Observable<Array<EnrichedOwnerDTO>> {
    return this.http.get<Array<EnrichedOwnerDTO>>(
      environment.backendUrl + '/owners/owners-with-pets', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<EnrichedOwnerDTO>>('ownersWithPets'))
    );
  }

  readOwners(request: ReadOwnersRequest): Observable<ReadOwnersResponse> {
    let params: HttpParams = new HttpParams();
    if (request.id !== null) {
      params = params.set('id', request.id.toString());
    }

    return this.http.get<ReadOwnersResponse>(
      environment.backendUrl + '/owners/read-owners', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<ReadOwnersResponse>('readOwners'))
    );
  }

  updateOwners(request: UpdateOwnersRequest): Observable<UpdateOwnersResponse> {
    return this.http.put<UpdateOwnersResponse>(
      environment.backendUrl + '/owners/update-owners', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<UpdateOwnersResponse>('updateOwners'))
    );
  }

  createOwners(request: CreateOwnersRequest): Observable<CreateOwnersResponse> {
    return this.http.post<CreateOwnersResponse>(
      environment.backendUrl + '/owners/create-owners', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<CreateOwnersResponse>('createOwners'))
    );
  }

  ownersPets(request: OwnersPetsRequest): Observable<Array<OwnersPetsResponse>> {
    return this.http.get<Array<OwnersPetsResponse>>(
      environment.backendUrl + '/owners/${request.ownerId}/pets', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<OwnersPetsResponse>>('ownersPets'))
    );
  }

  deleteOwners(request: DeleteOwnersRequest): Observable<Record<string, never>> {
    let params: HttpParams = new HttpParams();
    if (request.id !== null) {
      params = params.set('id', request.id.toString());
    }

    return this.http.delete<Record<string, never>>(
      environment.backendUrl + '/owners/delete-owners', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<Record<string, never>>('deleteOwners'))
    );
  }

  allOwners(request: AllOwnersRequest): Observable<PagedDto<AllOwnersResponse>> {
    let params: HttpParams = new HttpParams();
    if (request.drop !== null) {
      params = params.set('drop', request.drop.toString());
    }
    if (request.take !== null) {
      params = params.set('take', request.take.toString());
    }

    return this.http.get<PagedDto<AllOwnersResponse>>(
      environment.backendUrl + '/owners/all-owners', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<PagedDto<AllOwnersResponse>>('allOwners'))
    );
  }

  forAddress(request: ForAddressRequest): Observable<PagedDto<EnrichedOwnerDTO>> {
    let params: HttpParams = new HttpParams();
    if (request.address !== null) {
      params = params.set('address', request.address.toString());
    }
    if (request.drop !== null) {
      params = params.set('drop', request.drop.toString());
    }
    if (request.take !== null) {
      params = params.set('take', request.take.toString());
    }

    return this.http.get<PagedDto<EnrichedOwnerDTO>>(
      environment.backendUrl + '/owners/for-address', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<PagedDto<EnrichedOwnerDTO>>('forAddress'))
    );
  }

  myPets(): Observable<Array<MyPetsResponse>> {
    return this.http.get<Array<MyPetsResponse>>(
      environment.backendUrl + '/owners/my-pets', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<MyPetsResponse>>('myPets'))
    );
  }

  ownerVets(): Observable<Array<OwnerVetsResponse>> {
    return this.http.get<Array<OwnerVetsResponse>>(
      environment.backendUrl + '/owners/owner-vets', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<OwnerVetsResponse>>('ownerVets'))
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

export class CreateOwnersResponse {
  id: number;
  userId: number;
  address: string;
  city: string;
  telephone: string;

  constructor(id: number,
              userId: number,
              address: string,
              city: string,
              telephone: string) {
    this.id = id;
    this.userId = userId;
    this.address = address;
    this.city = city;
    this.telephone = telephone;
  }

}

export class AllOwnersResponse {
  id: number;
  userId: number;
  address: string;
  city: string;
  telephone: string;

  constructor(id: number,
              userId: number,
              address: string,
              city: string,
              telephone: string) {
    this.id = id;
    this.userId = userId;
    this.address = address;
    this.city = city;
    this.telephone = telephone;
  }

}

export class ForAddressRequest {
  address: string;
  drop: number;
  take: number;

  constructor(address: string,
              drop: number,
              take: number) {
    this.address = address;
    this.drop = drop;
    this.take = take;
  }

}

export class OwnersPetsRequest {
  ownerId: number;

  constructor(ownerId: number) {
    this.ownerId = ownerId;
  }

}

export class ReadOwnersResponse {
  id: number;
  userId: number;
  address: string;
  city: string;
  telephone: string;

  constructor(id: number,
              userId: number,
              address: string,
              city: string,
              telephone: string) {
    this.id = id;
    this.userId = userId;
    this.address = address;
    this.city = city;
    this.telephone = telephone;
  }

}

export class EnrichedOwnerDTO {
  id: number;
  email: string;
  firstName: string;
  lastName: string;

  constructor(id: number,
              email: string,
              firstName: string,
              lastName: string) {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

}

export class OwnersPetsResponse {
  id: number;
  ownerId: number;
  name: string;

  constructor(id: number,
              ownerId: number,
              name: string) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
  }

}

export class DeleteOwnersRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class MyPetsResponse {
  id: number;
  name: string;
  birthday: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(id: number,
              name: string,
              birthday: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.id = id;
    this.name = name;
    this.birthday = birthday;
    this.petType = petType;
    this.vaccinated = vaccinated;
  }

}

export class AllOwnersRequest {
  drop: number;
  take: number;

  constructor(drop: number,
              take: number) {
    this.drop = drop;
    this.take = take;
  }

}

export class OwnerVetsResponse {
  id: number;
  userId: number;
  address: string;
  city: string;
  telephone: string;
  userFirstName: string;
  userLastName: string;
  userEmail: string;
  userBirthday: Date;
  userActive: boolean;
  userRole: UserRole;

  constructor(id: number,
              userId: number,
              address: string,
              city: string,
              telephone: string,
              userFirstName: string,
              userLastName: string,
              userEmail: string,
              userBirthday: Date,
              userActive: boolean,
              userRole: UserRole) {
    this.id = id;
    this.userId = userId;
    this.address = address;
    this.city = city;
    this.telephone = telephone;
    this.userFirstName = userFirstName;
    this.userLastName = userLastName;
    this.userEmail = userEmail;
    this.userBirthday = userBirthday;
    this.userActive = userActive;
    this.userRole = userRole;
  }

}

export class UpdateOwnersResponse {
  id: number;
  userId: number;
  address: string;
  city: string;
  telephone: string;

  constructor(id: number,
              userId: number,
              address: string,
              city: string,
              telephone: string) {
    this.id = id;
    this.userId = userId;
    this.address = address;
    this.city = city;
    this.telephone = telephone;
  }

}

export class ReadOwnersRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class UpdateOwnersRequest {
  id: number;
  userId: number;
  address: string;
  city: string;
  telephone: string;

  constructor(id: number,
              userId: number,
              address: string,
              city: string,
              telephone: string) {
    this.id = id;
    this.userId = userId;
    this.address = address;
    this.city = city;
    this.telephone = telephone;
  }

}

export class CreateOwnersRequest {
  userId: number;
  address: string;
  city: string;
  telephone: string;

  constructor(userId: number,
              address: string,
              city: string,
              telephone: string) {
    this.userId = userId;
    this.address = address;
    this.city = city;
    this.telephone = telephone;
  }

}
