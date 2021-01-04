import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Meme } from '../../shared/models/Meme';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LikebuttonService {

  constructor(private http: HttpClient) { }

  public getMemeById(id: number):Observable<Meme>{
    return this.http.get<Meme>(`http://localhost:8080/meme/${id}`);
  }

  public updateMeme(meme: Meme){
    this.http.put<Meme>(`http://localhost:8080/meme/${meme.id}`, meme);
  }
}
