import {
  HttpClient,
  HttpEvent,
  HttpHeaders,
  HttpRequest,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Meme } from "app/models/Meme";
import { environment } from "environments/environment";
import { Observable, throwError } from "rxjs";
import { catchError, retry } from "rxjs/operators";

@Injectable({
  providedIn: "root",
})
export class MemeService {
  constructor(private http: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    }),
  };

  CreateMemeFormData(data: Meme): FormData {
    var result = new FormData();

    result.append("imageblob", data.imageblob);
    result.append("title", data.title);
    result.append("userId", data.id.toString())

    return result;
  }

  CreateMeme(data): Observable<HttpEvent<any>> {
    const req = new HttpRequest(
      "POST",
      `${environment.apiUrl}/meme/`,
      this.CreateMemeFormData(data),
      {
        reportProgress: true,
        responseType: "json",
      }
    );

    return this.http.request(req);
  }

  GetAllMemes(): Observable<Meme[]> {
    return this.http
      .get<Meme[]>(`${environment.apiUrl}/meme/`, this.httpOptions)
      .pipe(retry(1), catchError(this.handleError));
  }

  getMeme(id): Observable<Meme> {
    return this.http
      .get<Meme>(`${environment.apiUrl}/meme/` + id)
      .pipe(retry(1), catchError(this.handleError));
  }

  updateMeme(meme: Meme){
    return this.http.put<Meme>(`http://localhost:8080/meme/update`, meme);
  }

  // Error handling
  handleError(error) {
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
