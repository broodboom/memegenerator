import {
  HttpClient,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
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
  private isLoggedIn: boolean;

  constructor(protected httpClient: HttpClient) {}

  public loggedIn = () => this.isLoggedIn;

  public login(username: string, password: string): Observable<boolean> {
    const formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);

    return this.httpClient.post(`${environment.apiUrl}/login`, formData).pipe(
      tap((response: LoginResponse) => {
        this.isLoggedIn = response.status;
      }),
      map(() => this.isLoggedIn)
    );
  }

  public logout(): Observable<void> {
    return this.httpClient.post<void>(`${environment.apiUrl}/logout`, {}).pipe(
      tap(() => {
        this.isLoggedIn = false;
      })
    );
  }
}
