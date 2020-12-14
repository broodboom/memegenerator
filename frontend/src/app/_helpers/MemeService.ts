import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { Meme } from '../shared/models/Meme';

const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
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

  
  httpOptions2 = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json',
      'Access-Control-Allow-Origin': '*'
    })
  }

  CreateMemeFormData(data: Meme): FormData{
    var result = new FormData();

    result.append("imageblob", data.imageblob);
    result.append("title", data.title);

    return result;
  }

  // CreateMeme(data): Observable<Meme> {
  //   return this.http.post<Meme>(`${environment.apiUrl}/meme/`,  this.CreateMemeFormData(data), this.httpOptions2)
  //   .pipe(
  //     retry(1),
  //     catchError(this.errorHandl)
  //   )
  // }

    CreateMeme(data): Observable<HttpEvent<any>> {
      const req = new HttpRequest('POST', `${environment.apiUrl}/meme/`, this.CreateMemeFormData(data), {
        reportProgress: true,
        responseType: 'json'
      });
  
      return this.http.request(req);
    }

  GetAllMemes(): Observable<Meme[]> {
    return this.http.get<Meme[]>(`${environment.apiUrl}/meme/`, this.httpOptions)
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