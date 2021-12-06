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

  getActiveUser(request: UserDTO): Observable<UserResponseDTO> {
    let params: HttpParams = new HttpParams();
    if (request.id !== null) {
      params = params.set('id', request.id.toString());
    }
    if (request.email !== null) {
      params = params.set('email', request.email.toString());
    }
    if (request.firstName !== null) {
      params = params.set('firstName', request.firstName.toString());
    }
    if (request.lastName !== null) {
      params = params.set('lastName', request.lastName.toString());
    }
    if (request.birthdate !== null) {
      params = params.set('birthdate', request.birthdate.toString());
    }
    if (request.active !== null) {
      params = params.set('active', request.active.toString());
    }

    return this.http.get<UserResponseDTO>(
      environment.backendUrl + '/users/user', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<UserResponseDTO>('getActiveUser'))
    );
  }

  setUserActiveStatusSimple(request: UserActivationSimpleDTO): Observable<UserResponseDTO> {
    return this.http.put<UserResponseDTO>(
      environment.backendUrl + '/users/active/simple', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<UserResponseDTO>('setUserActiveStatusSimple'))
    );
  }

  nonAdmins(): Observable<Array<NonAdminsResponse>> {
    return this.http.get<Array<NonAdminsResponse>>(
      environment.backendUrl + '/users/nonAdmins', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<NonAdminsResponse>>('nonAdmins'))
    );
  }

  deleteUser(request: DeleteUserRequest): Observable<Record<string, never>> {
    let params: HttpParams = new HttpParams();
    if (request.id !== null) {
      params = params.set('id', request.id.toString());
    }

    return this.http.delete<Record<string, never>>(
      environment.backendUrl + '/users/delete-user', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<Record<string, never>>('deleteUser'))
    );
  }

  updateUser(request: UpdateUserRequest): Observable<UpdateUserResponse> {
    return this.http.put<UpdateUserResponse>(
      environment.backendUrl + '/users/update-user', request, {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<UpdateUserResponse>('updateUser'))
    );
  }

  createUser(request: CreateUserRequest): Observable<CreateUserResponse> {
    return this.http.post<CreateUserResponse>(
      environment.backendUrl + '/users/create-user', request, {
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
      environment.backendUrl + '/users/read-user', {
        headers: this.header(),
        params
      }).pipe(
      catchError(this.handleError<ReadUserResponse>('readUser'))
    );
  }

  users(): Observable<Array<UsersResponse>> {
    return this.http.get<Array<UsersResponse>>(
      environment.backendUrl + '/users/allUsers', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<UsersResponse>>('users'))
    );
  }

  adminUsers(): Observable<Array<AdminUsersResponse>> {
    return this.http.get<Array<AdminUsersResponse>>(
      environment.backendUrl + '/users/admins', {
        headers: this.header()
      }).pipe(
      catchError(this.handleError<Array<AdminUsersResponse>>('adminUsers'))
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

export class UserResponseDTO {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  birthdate: Date;
  active: boolean;

  constructor(id: number,
              email: string,
              firstName: string,
              lastName: string,
              birthdate: Date,
              active: boolean) {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
    this.active = active;
  }

}

export class AdminUsersResponse {
  id: number;
  firstName: string;
  lastName: string;
  birthdate: Date;
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
              birthdate: Date,
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
    this.birthdate = birthdate;
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

export class ReadUserRequest {
  id: number;

  constructor(id: number) {
    this.id = id;
  }

}

export class UsersResponse {
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

export class UserDTO {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  birthdate: Date;
  active: boolean;

  constructor(id: number,
              email: string,
              firstName: string,
              lastName: string,
              birthdate: Date,
              active: boolean) {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
    this.active = active;
  }

}

export class NonAdminsResponse {
  id: number;
  firstName: string;
  lastName: string;
  birthdate: Date;
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
              birthdate: Date,
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
    this.birthdate = birthdate;
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

export class UserActivationSimpleDTO {
  id: number;
  active: boolean;

  constructor(id: number,
              active: boolean) {
    this.id = id;
    this.active = active;
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
  birthdate: Date;
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
              birthdate: Date,
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
    this.birthdate = birthdate;
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

export class UpdateUserResponse {
  id: number;
  firstName: string;
  lastName: string;
  birthdate: Date;
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
              birthdate: Date,
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
    this.birthdate = birthdate;
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
  birthdate: Date;
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
              birthdate: Date,
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
    this.birthdate = birthdate;
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
  birthdate: Date;
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
              birthdate: Date,
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
    this.birthdate = birthdate;
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
  birthdate: Date;
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
              birthdate: Date,
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
    this.birthdate = birthdate;
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
