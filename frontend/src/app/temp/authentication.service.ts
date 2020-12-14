import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from 'app/models/User';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {




    private currentUserSubject: BehaviorSubject<any>;
    public currentUser: Observable<User>;

    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    login(username: string, password: string) {

      const headers = new HttpHeaders({
        authorization : 'Basic ' + btoa(username + ':' + password)
      });

      return this.http.get('user', {headers: headers})
          .pipe(map(user => {
              // login successful if there's a jwt token in the response
              if (user) {
                  // store user details and jwt token in local storage to keep user logged in between page refreshes
                  localStorage.setItem('currentUser', JSON.stringify(user));
                  this.currentUserSubject.next(null);
              }

              return user;
          }));
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
}
