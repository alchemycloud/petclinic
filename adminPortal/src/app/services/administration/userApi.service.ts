import {environment} from '../../../environments/environment';
import {TraceService} from '../traceService.service';
import {UserRole} from './enums';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable()
export class UserApiService {
  private readonly applicationJson = 'application/json';
  private readonly bearer = 'Bearer ';

  private header() {
    return new HttpHeaders({
      'content-type': this.applicationJson
    });
  }


  constructor(private readonly http: HttpClient, private readonly traceService: TraceService) {

  }

  getActiveUser(request: GetActiveUserRequest): Observable<GetActiveUserResponse> {
    return this.http.get<GetActiveUserResponse>(
      environment.administrationUrl + '/users/active/${request.userId}', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<GetActiveUserResponse>('getActiveUser'))
    );
  }

  activateUser(request: UserActivationDTO): Observable<Record<string, never>> {
    return this.http.put<Record<string, never>>(
      environment.administrationUrl + '/users/activate', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Record<string, never>>('activateUser'))
    );
  }

  nonAdmins(): Observable<Array<NonAdminsResponse>> {
    return this.http.get<Array<NonAdminsResponse>>(
      environment.administrationUrl + '/users/non-admins', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<NonAdminsResponse>>('nonAdmins'))
    );
  }

  removeUserFromTenant(request: RemoveUserFromTenantRequest): Observable<Record<string, never>> {
    return this.http.put<Record<string, never>>(
      environment.administrationUrl + '/users/remove-user-from-tenant', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Record<string, never>>('removeUserFromTenant'))
    );
  }

  deleteUser(request: DeleteUserRequest): Observable<Record<string, never>> {
    let params: HttpParams = new HttpParams();
    if (request.id !== null) {
      params = params.set('id', request.id.toString());
    }

    return this.http.delete<Record<string, never>>(
      environment.administrationUrl + '/users/delete-user', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<Record<string, never>>('deleteUser'))
    );
  }

  updateUser(request: UpdateUserRequest): Observable<UpdateUserResponse> {
    return this.http.put<UpdateUserResponse>(
      environment.administrationUrl + '/users/update-user', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<UpdateUserResponse>('updateUser'))
    );
  }

  createUser(request: CreateUserRequest): Observable<CreateUserResponse> {
    return this.http.post<CreateUserResponse>(
      environment.administrationUrl + '/users/create-user', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<CreateUserResponse>('createUser'))
    );
  }

  readUser(request: ReadUserRequest): Observable<ReadUserResponse> {
    let params: HttpParams = new HttpParams();
    if (request.id !== null) {
      params = params.set('id', request.id.toString());
    }

    return this.http.get<ReadUserResponse>(
      environment.administrationUrl + '/users/read-user', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<ReadUserResponse>('readUser'))
    );
  }

  admins(): Observable<Array<AdminsResponse>> {
    return this.http.get<Array<AdminsResponse>>(
      environment.administrationUrl + '/users/admins', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<AdminsResponse>>('admins'))
    );
  }

  createUserOnTenant(request: CreateUserOnTenantRequest): Observable<Record<string, never>> {
    return this.http.post<Record<string, never>>(
      environment.administrationUrl + '/users/create-user-on-tenant', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Record<string, never>>('createUserOnTenant'))
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

export class ReadUserRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class NonAdminsResponse {
  id: number;
  firstName: string;
  lastName: string;
  birthday: Date;
  active: boolean;
  role: UserRole;
  email: string;
  passwordHash: string;
  emailVerificationCode: string;
  emailVerificationCodeTimestamp: Date;
  emailVerified: boolean;
  resetPasswordCode: string;
  resetPasswordCodeTimestamp: Date;

  constructor(id: number,
              firstName: string,
              lastName: string,
              birthday: Date,
              active: boolean,
              role: UserRole,
              email: string,
              passwordHash: string,
              emailVerificationCode: string,
              emailVerificationCodeTimestamp: Date,
              emailVerified: boolean,
              resetPasswordCode: string,
              resetPasswordCodeTimestamp: Date) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.active = active;
    this.role = role;
    this.email = email;
    this.passwordHash = passwordHash;
    this.emailVerificationCode = emailVerificationCode;
    this.emailVerificationCodeTimestamp = emailVerificationCodeTimestamp;
    this.emailVerified = emailVerified;
    this.resetPasswordCode = resetPasswordCode;
    this.resetPasswordCodeTimestamp = resetPasswordCodeTimestamp;
  }

}

export class CreateUserOnTenantRequest {
  tenant: string;
  email: string;
  role: UserRole;

  constructor(tenant: string,
              email: string,
              role: UserRole) {
    this.tenant = tenant;
    this.email = email;
    this.role = role;
  }

}

export class AdminsResponse {
  id: number;
  firstName: string;
  lastName: string;
  birthday: Date;
  active: boolean;
  role: UserRole;
  email: string;
  passwordHash: string;
  emailVerificationCode: string;
  emailVerificationCodeTimestamp: Date;
  emailVerified: boolean;
  resetPasswordCode: string;
  resetPasswordCodeTimestamp: Date;

  constructor(id: number,
              firstName: string,
              lastName: string,
              birthday: Date,
              active: boolean,
              role: UserRole,
              email: string,
              passwordHash: string,
              emailVerificationCode: string,
              emailVerificationCodeTimestamp: Date,
              emailVerified: boolean,
              resetPasswordCode: string,
              resetPasswordCodeTimestamp: Date) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.active = active;
    this.role = role;
    this.email = email;
    this.passwordHash = passwordHash;
    this.emailVerificationCode = emailVerificationCode;
    this.emailVerificationCodeTimestamp = emailVerificationCodeTimestamp;
    this.emailVerified = emailVerified;
    this.resetPasswordCode = resetPasswordCode;
    this.resetPasswordCodeTimestamp = resetPasswordCodeTimestamp;
  }

}

export class RemoveUserFromTenantRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class GetActiveUserRequest {
  userId: number;

  constructor(userId: number) {
    this.userId = userId;
  }

}

export class DeleteUserRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class CreateUserResponse {
  id: number;
  firstName: string;
  lastName: string;
  birthday: Date;
  active: boolean;
  role: UserRole;
  email: string;
  passwordHash: string;
  emailVerificationCode: string;
  emailVerificationCodeTimestamp: Date;
  emailVerified: boolean;
  resetPasswordCode: string;
  resetPasswordCodeTimestamp: Date;

  constructor(id: number,
              firstName: string,
              lastName: string,
              birthday: Date,
              active: boolean,
              role: UserRole,
              email: string,
              passwordHash: string,
              emailVerificationCode: string,
              emailVerificationCodeTimestamp: Date,
              emailVerified: boolean,
              resetPasswordCode: string,
              resetPasswordCodeTimestamp: Date) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.active = active;
    this.role = role;
    this.email = email;
    this.passwordHash = passwordHash;
    this.emailVerificationCode = emailVerificationCode;
    this.emailVerificationCodeTimestamp = emailVerificationCodeTimestamp;
    this.emailVerified = emailVerified;
    this.resetPasswordCode = resetPasswordCode;
    this.resetPasswordCodeTimestamp = resetPasswordCodeTimestamp;
  }

}

export class UserActivationDTO {
  id: number;
  active: boolean;

  constructor(id: number,
              active: boolean) {
    this.id = id;
    this.active = active;
  }

}

export class UpdateUserResponse {
  id: number;
  firstName: string;
  lastName: string;
  birthday: Date;
  active: boolean;
  role: UserRole;
  email: string;
  passwordHash: string;
  emailVerificationCode: string;
  emailVerificationCodeTimestamp: Date;
  emailVerified: boolean;
  resetPasswordCode: string;
  resetPasswordCodeTimestamp: Date;

  constructor(id: number,
              firstName: string,
              lastName: string,
              birthday: Date,
              active: boolean,
              role: UserRole,
              email: string,
              passwordHash: string,
              emailVerificationCode: string,
              emailVerificationCodeTimestamp: Date,
              emailVerified: boolean,
              resetPasswordCode: string,
              resetPasswordCodeTimestamp: Date) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.active = active;
    this.role = role;
    this.email = email;
    this.passwordHash = passwordHash;
    this.emailVerificationCode = emailVerificationCode;
    this.emailVerificationCodeTimestamp = emailVerificationCodeTimestamp;
    this.emailVerified = emailVerified;
    this.resetPasswordCode = resetPasswordCode;
    this.resetPasswordCodeTimestamp = resetPasswordCodeTimestamp;
  }

}

export class CreateUserRequest {
  firstName: string;
  lastName: string;
  birthday: Date;
  active: boolean;
  role: UserRole;
  email: string;
  passwordHash: string;
  emailVerificationCode: string;
  emailVerificationCodeTimestamp: Date;
  emailVerified: boolean;
  resetPasswordCode: string;
  resetPasswordCodeTimestamp: Date;

  constructor(firstName: string,
              lastName: string,
              birthday: Date,
              active: boolean,
              role: UserRole,
              email: string,
              passwordHash: string,
              emailVerificationCode: string,
              emailVerificationCodeTimestamp: Date,
              emailVerified: boolean,
              resetPasswordCode: string,
              resetPasswordCodeTimestamp: Date) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.active = active;
    this.role = role;
    this.email = email;
    this.passwordHash = passwordHash;
    this.emailVerificationCode = emailVerificationCode;
    this.emailVerificationCodeTimestamp = emailVerificationCodeTimestamp;
    this.emailVerified = emailVerified;
    this.resetPasswordCode = resetPasswordCode;
    this.resetPasswordCodeTimestamp = resetPasswordCodeTimestamp;
  }

}

export class ReadUserResponse {
  id: number;
  firstName: string;
  lastName: string;
  birthday: Date;
  active: boolean;
  role: UserRole;
  email: string;
  passwordHash: string;
  emailVerificationCode: string;
  emailVerificationCodeTimestamp: Date;
  emailVerified: boolean;
  resetPasswordCode: string;
  resetPasswordCodeTimestamp: Date;

  constructor(id: number,
              firstName: string,
              lastName: string,
              birthday: Date,
              active: boolean,
              role: UserRole,
              email: string,
              passwordHash: string,
              emailVerificationCode: string,
              emailVerificationCodeTimestamp: Date,
              emailVerified: boolean,
              resetPasswordCode: string,
              resetPasswordCodeTimestamp: Date) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.active = active;
    this.role = role;
    this.email = email;
    this.passwordHash = passwordHash;
    this.emailVerificationCode = emailVerificationCode;
    this.emailVerificationCodeTimestamp = emailVerificationCodeTimestamp;
    this.emailVerified = emailVerified;
    this.resetPasswordCode = resetPasswordCode;
    this.resetPasswordCodeTimestamp = resetPasswordCodeTimestamp;
  }

}

export class UpdateUserRequest {
  id: number;
  firstName: string;
  lastName: string;
  birthday: Date;
  active: boolean;
  role: UserRole;
  email: string;
  passwordHash: string;
  emailVerificationCode: string;
  emailVerificationCodeTimestamp: Date;
  emailVerified: boolean;
  resetPasswordCode: string;
  resetPasswordCodeTimestamp: Date;

  constructor(id: number,
              firstName: string,
              lastName: string,
              birthday: Date,
              active: boolean,
              role: UserRole,
              email: string,
              passwordHash: string,
              emailVerificationCode: string,
              emailVerificationCodeTimestamp: Date,
              emailVerified: boolean,
              resetPasswordCode: string,
              resetPasswordCodeTimestamp: Date) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.active = active;
    this.role = role;
    this.email = email;
    this.passwordHash = passwordHash;
    this.emailVerificationCode = emailVerificationCode;
    this.emailVerificationCodeTimestamp = emailVerificationCodeTimestamp;
    this.emailVerified = emailVerified;
    this.resetPasswordCode = resetPasswordCode;
    this.resetPasswordCodeTimestamp = resetPasswordCodeTimestamp;
  }

}

export class GetActiveUserResponse {
  id: number;
  firstName: string;
  lastName: string;
  birthday: Date;
  active: boolean;
  role: UserRole;
  email: string;
  passwordHash: string;
  emailVerificationCode: string;
  emailVerificationCodeTimestamp: Date;
  emailVerified: boolean;
  resetPasswordCode: string;
  resetPasswordCodeTimestamp: Date;

  constructor(id: number,
              firstName: string,
              lastName: string,
              birthday: Date,
              active: boolean,
              role: UserRole,
              email: string,
              passwordHash: string,
              emailVerificationCode: string,
              emailVerificationCodeTimestamp: Date,
              emailVerified: boolean,
              resetPasswordCode: string,
              resetPasswordCodeTimestamp: Date) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.active = active;
    this.role = role;
    this.email = email;
    this.passwordHash = passwordHash;
    this.emailVerificationCode = emailVerificationCode;
    this.emailVerificationCodeTimestamp = emailVerificationCodeTimestamp;
    this.emailVerified = emailVerified;
    this.resetPasswordCode = resetPasswordCode;
    this.resetPasswordCodeTimestamp = resetPasswordCodeTimestamp;
  }

}
