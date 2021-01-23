import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Tag } from "app/models/Tag";
import { Observable } from "rxjs";
import { retry } from "rxjs/operators";


@Injectable({
    providedIn: 'root'
})
export class TagService {
    constructor(private http: HttpClient) { }

    httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      }),
      observe: "response" as const
    };

    createTag(tag: Tag): Observable<any>{
        return this.http.post<Tag>(`http://localhost:8080/tag/create/${tag.id}`, tag, this.httpOptions).pipe(retry(1));
    }

    getTags():Observable<any>{
        return this.http.get<Tag[]>(`http://localhost:8080/tag/`, this.httpOptions);
    }
}
