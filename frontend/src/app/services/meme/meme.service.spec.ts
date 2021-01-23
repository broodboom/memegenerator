import { TestBed } from '@angular/core/testing';
import { Meme } from 'app/models/Meme';
import { Observable } from 'rxjs';
import { MemeService } from './meme.service';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';

describe('MemeService', () => {
  let service: MemeService;

  let httpMock : HttpTestingController;

  let httpClient : HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(MemeService);
    httpMock = TestBed.get(HttpTestingController);
    httpClient = TestBed.get(HttpClient);
  });

  // Test creation
  it('should create a meme', () => {

    const meme: Meme = {
      id: 1,
      title: 'Test meme',
      imageblob: new Blob(),
      categoryId: 0,
      userId: 0
    };

    service.CreateMeme(meme).subscribe((m)=>{

      expect(m.body).toEqual(meme);
    });
     
    const request = httpMock.expectOne('http://localhost:8080/meme/');

    expect(request.request.method).toEqual('POST');

    request.flush(meme);

    httpMock.verify();
  });

  // Test fetching all
  it('should get all memes', () => {

    const fakeMemes : Meme[] = []

    for(let i = 0; i < 5; i ++){
      const meme: Meme = {
        id: i,
        title: `Test meme ${i}`,
        imageblob: new Blob(),
        categoryId: 0,
        userId: 0
      };

      fakeMemes.push(meme)
    }

    service.GetAllMemes().subscribe((m)=>{
      console.log(`Memes: ${JSON.stringify(m)}`)

      expect(m).toEqual(fakeMemes)
    });
     
    const request = httpMock.expectOne('http://localhost:8080/meme/');

    expect(request.request.method).toEqual('GET');

    request.flush(fakeMemes);

    httpMock.verify();
  });
});
