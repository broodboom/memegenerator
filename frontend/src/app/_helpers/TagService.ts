import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Tag } from "app/shared/models/Tag";
import { Observable } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class TagService {
    constructor(private http: HttpClient) { }

    public createTag(tag: Tag){
        this.http.post<Tag>(`http://localhost:8080/tag/${tag.id}`, tag);
    }

    public getTags():Observable<Tag[]>{
        return this.http.get<Tag[]>(`http://localhost:8080/tag/`);
    }
}