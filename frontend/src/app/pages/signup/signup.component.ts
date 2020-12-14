import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { User } from '../../shared/models/User'
import { retry, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';

@Component({
  selector: 'ngx-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})

export class SignupComponent implements OnInit {

  signupForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    email: new FormControl('')
  })

  user: User

  constructor(private formBuilder: FormBuilder, private httpClient: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json',
      'Access-Control-Allow-Origin': '*'
    })
  }

  ngOnInit(): void {

    this.user = {
      username: '',
      password: '',
      email: ''
    }

    this.signupForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required]
    });
  }

  get f() { return this.signupForm.controls; }

  signup(): void {

    const e: Event = window.event
    e.preventDefault()

    this.user = {
      username: this.f.username.value,
      password: this.f.password.value,
      email: this.f.email.value
    }

    const environment = {
      production: false,
      apiUrl: 'http://localhost:8080'
    };

    // Default options are marked with *
    // const response = fetch('localhost:8080/users', {
    //   method: 'POST',
    //   mode: 'cors',
    //   headers: {
    //     'Content-Type': 'application/json'
    //   },
    //   body: JSON.stringify(this.user) // body data type must match "Content-Type" header
    // })

      this.httpClient.post<User>(`${environment.apiUrl}/users`,  this.user, this.httpOptions)
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