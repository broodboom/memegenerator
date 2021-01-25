import {
  HttpClient,
  HttpHeaders,
} from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { FormsModule, ReactiveFormsModule,FormGroup, FormBuilder, Validators } from '@angular/forms';
import { throwError } from "rxjs";
import { User } from "app/models/User";

@Component({
  selector: "ngx-signup",
  templateUrl: "./signup.component.html",
  styleUrls: ["./signup.component.scss"],
})
export class SignupComponent implements OnInit {

  user: User;
  signupForm: FormGroup;
  showLoginForm: Boolean;


  constructor(
    
    private formBuilder: FormBuilder,
    private httpClient: HttpClient,
  ) {}

  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    }),
  };

  ngOnInit(): void {
    this.user = {
      username: "",
      password: "",
      email: "",
    };

    this.signupForm = this.formBuilder.group({
      username: ["", Validators.required],
      password: ["", Validators.required],
      email: ["", Validators.required],
    });

    this.showLoginForm = false
  }

  get f() {
    return this.signupForm.controls;
  }

  signup(): void {
    const e: Event = window.event;
    e.preventDefault();

    this.user = {
      username: this.f.username.value,
      password: this.f.password.value,
      email: this.f.email.value,
    };

    const environment = {
      production: false,
      apiUrl: "http://localhost:8080",
    };

    this.httpClient
      .post<User>(`${environment.apiUrl}/user`, this.user, this.httpOptions)
      .subscribe((res) => {

        this.showLoginForm = true
        
        console.log(res);
      });
  }

  // Error handling
  errorHandl(error) {
    let errorMessage = "";
    if (error.error instanceof ErrorEvent) {
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