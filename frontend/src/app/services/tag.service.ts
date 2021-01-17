import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Tag } from "app/models/Tag";
import { Observable } from "rxjs";
import { retry } from "rxjs/operators";


@Injectable({
    providedIn: 'root'
})
export class TagService {
    constructor(private http: HttpClient) { }

    createTag(tag: Tag): Observable<any>{
        return this.http.post<Tag>(`http://localhost:8080/tag/create`, tag, {observe: "response" as const,}).pipe(retry(1));
    }

    public getTags():Observable<Tag[]>{
        return this.http.get<Tag[]>(`http://localhost:8080/tag/`);
    }
}