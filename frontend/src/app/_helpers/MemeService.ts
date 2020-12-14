import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { Meme } from '../shared/models/meme';

const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};

@Injectable()
export class MemeService {
  public baseurl: "http://localhost:51049/api/";

  constructor(public http: HttpClient) {

   }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json',
      'Access-Control-Allow-Origin': '*'
    })
  }

  CreateMeme(data): Observable<Meme> {
    return this.http.post<Meme>(`${environment.apiUrl}/meme`, JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  GetAllMemes(): Observable<Meme[]> {
    return this.http.get<Meme[]>(`${environment.apiUrl}/meme`, this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  getMeme(id): Observable<Meme> {
    return this.http.get<Meme>(`${environment.apiUrl}/meme/`+ id)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  updateMeme(meme){
    this.http.put<Meme>(`${environment.apiUrl}/meme/${meme.id}`, meme)
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