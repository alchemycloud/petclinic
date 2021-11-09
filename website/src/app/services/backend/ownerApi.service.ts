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
      'Content-Type': this.applicationJson
    });
  }


  constructor(private readonly http: HttpClient, private readonly traceService: TraceService) {

  }

  ownersWithPets(): Observable<Array<OwnersWithPetsResponse>> {
    return this.http.get<Array<OwnersWithPetsResponse>>(
      environment.backendUrl + '/ownersWithPets', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<OwnersWithPetsResponse>>('ownersWithPets'))
    );
  }

  ownersForAddress(request: OwnersForAddressRequest): Observable<PagedDto<OwnersForAddressResponse>> {
    let params: HttpParams = new HttpParams();
    if (request.address != null) {
      params = params.set('address', request.address.toString());
    }
    if (request.drop != null) {
      params = params.set('drop', request.drop.toString());
    }
    if (request.take != null) {
      params = params.set('take', request.take.toString());
    }

    return this.http.get<PagedDto<OwnersForAddressResponse>>(
      environment.backendUrl + '/owners', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<PagedDto<OwnersForAddressResponse>>('ownersForAddress'))
    );
  }

  createOwner(request: CreateOwnerRequest): Observable<CreateOwnerResponse> {
    return this.http.post<CreateOwnerResponse>(
      environment.backendUrl + '/owner', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<CreateOwnerResponse>('createOwner'))
    );
  }

  ownersPets(request: OwnersPetsRequest): Observable<Array<OwnersPetsResponse>> {
    return this.http.get<Array<OwnersPetsResponse>>(
      environment.backendUrl + '/owner/${request.ownerId}/pets', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<OwnersPetsResponse>>('ownersPets'))
    );
  }

  deleteOwner(request: DeleteOwnerRequest): Observable<{}> {
    return this.http.delete<{}>(
      environment.backendUrl + '/owner/${request.id}', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<{}>('deleteOwner'))
    );
  }

  updateOwner(request: UpdateOwnerRequest): Observable<UpdateOwnerResponse> {
    return this.http.put<UpdateOwnerResponse>(
      environment.backendUrl + '/owner/${request.id}', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<UpdateOwnerResponse>('updateOwner'))
    );
  }

  allOwners(request: AllOwnersRequest): Observable<Array<AllOwnersResponse>> {
    let params: HttpParams = new HttpParams();
    if (request.param != null) {
      params = params.set('param', request.param.toString());
    }

    return this.http.get<Array<AllOwnersResponse>>(
      environment.backendUrl + '/allOwners', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<Array<AllOwnersResponse>>('allOwners'))
    );
  }

  readOwner(request: ReadOwnerRequest): Observable<ReadOwnerResponse> {
    return this.http.get<ReadOwnerResponse>(
      environment.backendUrl + '/owner/${request.id}', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<ReadOwnerResponse>('readOwner'))
    );
  }

  myPets(): Observable<Array<MyPetsResponse>> {
    return this.http.get<Array<MyPetsResponse>>(
      environment.backendUrl + '/my-pets', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<MyPetsResponse>>('myPets'))
    );
  }

  ownerVets(): Observable<Array<OwnerVetsResponse>> {
    return this.http.get<Array<OwnerVetsResponse>>(
      environment.backendUrl + '/owner-vets', {
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

export class CreateOwnerRequest {
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

export class CreateOwnerResponse {
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

export class UpdateOwnerRequest {
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

export class DeleteOwnerRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
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

export class ReadOwnerRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class OwnersPetsRequest {
  ownerId: number;

  constructor(ownerId: number) {
    this.ownerId = ownerId;
  }

}

export class OwnersForAddressResponse {
  id: number;
  userEmail: string;
  userFirstName: string;
  userLastName: string;

  constructor(id: number,
              userEmail: string,
              userFirstName: string,
              userLastName: string) {
    this.id = id;
    this.userEmail = userEmail;
    this.userFirstName = userFirstName;
    this.userLastName = userLastName;
  }

}

export class UpdateOwnerResponse {
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

export class OwnersPetsResponse {
  petId: number;
  id: number;
  petName: string;

  constructor(petId: number,
              id: number,
              petName: string) {
    this.petId = petId;
    this.id = id;
    this.petName = petName;
  }

}

export class ReadOwnerResponse {
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

export class MyPetsResponse {
  id: number;
  userId: number;
  address: string;
  city: string;
  telephone: string;
  petId: number;
  petOwnerId: number;
  petName: string;
  petBirthdate: Date;
  petPetType: PetType;
  petVaccinated: boolean;
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
              userId: number,
              address: string,
              city: string,
              telephone: string,
              petId: number,
              petOwnerId: number,
              petName: string,
              petBirthdate: Date,
              petPetType: PetType,
              petVaccinated: boolean,
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
    this.userId = userId;
    this.address = address;
    this.city = city;
    this.telephone = telephone;
    this.petId = petId;
    this.petOwnerId = petOwnerId;
    this.petName = petName;
    this.petBirthdate = petBirthdate;
    this.petPetType = petPetType;
    this.petVaccinated = petVaccinated;
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

export class AllOwnersRequest {
  param: number;

  constructor(param: number) {
    this.param = param;
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
              userId: number,
              address: string,
              city: string,
              telephone: string,
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
    this.userId = userId;
    this.address = address;
    this.city = city;
    this.telephone = telephone;
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

export class OwnersWithPetsResponse {
  userFirstName: string;
  userLastName: string;

  constructor(userFirstName: string,
              userLastName: string) {
    this.userFirstName = userFirstName;
    this.userLastName = userLastName;
  }

}

export class OwnersForAddressRequest {
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
