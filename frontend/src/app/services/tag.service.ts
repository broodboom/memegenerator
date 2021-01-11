import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Tag } from "app/models/Tag";
import { Observable } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class TagService {
    constructor(private http: HttpClient) { }

    public createTag(tag: Tag){
        return this.http.post<Tag>(`http://localhost:8080/tag/create`, tag);
    }

    public getTags():Observable<Tag[]>{
        return this.http.get<Tag[]>(`http://localhost:8080/tag/`);
    }
}