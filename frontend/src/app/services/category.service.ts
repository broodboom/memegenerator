import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Category } from "app/models/Category";
import { Observable } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class CategoryService {
    constructor(private http: HttpClient) { }

    public getCategories():Observable<Category[]>{
        return this.http.get<Category[]>(`http://localhost:8080/category/`);
    }
}