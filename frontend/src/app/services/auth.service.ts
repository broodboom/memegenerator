import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "app/models/User";
import { environment } from "environments/environment";
import { Observable } from "rxjs";
import { map, tap } from "rxjs/operators";

export interface LoginResponse {
  status: boolean;
}

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private _loggedIn: boolean;
  private _currentUser: User;

  constructor(protected httpClient: HttpClient) {}

  public loggedIn = () => this._loggedIn;

  public currentUser = () => this.currentUser;

  public login(username: string, password: string): Observable<boolean> {
    const formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);

    return this.httpClient.post(`${environment.apiUrl}/login`, formData).pipe(
      tap((response: LoginResponse) => {
        this._loggedIn = response.status;

        if (this._loggedIn) {

        }
      }),
      map(() => this._loggedIn)
    );
  }

  public logout(): Observable<void> {
    return this.httpClient.post<void>(`${environment.apiUrl}/logout`, {}).pipe(
      tap(() => {
        this._loggedIn = false;
      })
    );
  }
}
