import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { User } from 'app/shared/models/User';
import { userInfo } from 'os';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FormControl } from '@angular/forms';

const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};

let self: any;

@Component({
  selector: 'ngx-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})

export class ProfileComponent implements OnInit {
  constructor(public http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json',
      'Access-Control-Allow-Origin': '*'
    })
  }

  user: User;

  ngOnInit(): void {
    self = this;

    this.getUserInfo();
  }

  getUserInfo() {
      this.http.get<User>(`${environment.apiUrl}/users/`+ 10)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      ).subscribe(user2 => self.user = user2)
  }

  updateUser(): void {

    const e: Event = window.event
    e.preventDefault()

    debugger;

    self.user.email = (<HTMLInputElement>document.getElementById("email")).value;
    self.user.password = (<HTMLInputElement>document.getElementById("password")).value;

      this.http.put<User>(`${environment.apiUrl}/users`,  this.user, this.httpOptions)
      .subscribe(res => {
        console.log(res);
      });
  }

  // Error handling
  errorHandl(error) {
    let errorMessage = '';
    if(error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
 }
}
