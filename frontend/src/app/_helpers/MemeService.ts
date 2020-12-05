import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { Meme } from '../shared/models/Meme';


@Injectable({providedIn: 'root'})
export class MemeService {
  private url: "https://localhost:44386/api/";

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json'
    })
  }

  CreateMeme(data): Observable<Meme> {
    return this.http.post<Meme>(this.url + '/meme/', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  GetAllMemes(): Observable<Meme[]> {
    return this.http.get<Meme[]>(this.url + '/getmemes/')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  getMeme(id): Observable<Meme> {
    return this.http.get<Meme>(this.url + '/getmeme/' + id)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
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