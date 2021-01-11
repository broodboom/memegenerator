import { TestBed } from '@angular/core/testing';
import { Meme } from 'app/models/Meme';
import { Observable } from 'rxjs';
import { MemeService } from './meme.service';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import { HttpClient } from '@angular/common/http';

export class HttpClientStub {
  request(): Observable<any> {
    return Observable.of({});
  }

  get(): Observable<any> {
    // Collection of fake memes
    const memes: Meme[] = [];

    // Create collection of fake memes
    for (let i = 0; i < 5; i++) {
      const meme: Meme = {
        id: 0,
        title: `Vis meme ${i}`,
        imageblob: new Blob(),
        categoryId: 1,
        userId: 1
      };

      memes.push(meme)
    };

    return Observable.of(memes);
  }

  put(): Observable<any> {
    return Observable.of({});
  }
}

describe('MemeService', () => {
  let service: MemeService;

  const httpClientStub = new HttpClientStub();

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {
          provide: HttpClient,
          useValue: httpClientStub
        }
      ]
    });
    service = TestBed.inject(MemeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return a formdata object', () => {
    const memeId = 0;
    const memeTitle = 'abc';
    const memeImage = new Blob();
    const categoryId = 0;
    const userId = 0;

    const dataMock: Meme = {
      id: memeId,
      title: memeTitle,
      imageblob: memeImage,
      categoryId: categoryId,
      userId: userId
    };

    const result = service.CreateMemeFormData(dataMock);

    expect(result.get("title")).toEqual(memeTitle);
  });

  it('should create a meme', (done) => {
    // assign
    const memeId = 0;
    const memeTitle = 'abc';
    const memeImage = new Blob();
    const categoryId = 0;
    const userId = 0;

    const dataMock: Meme = {
      id: memeId,
      title: memeTitle,
      imageblob: memeImage,
      categoryId: categoryId,
      userId: userId
    };

    const formDataMock = new FormData();

    formDataMock.append("imageblob", dataMock.imageblob);
    formDataMock.append("title", dataMock.title);
    formDataMock.append("userId", dataMock.id.toString());

    spyOn(service, 'CreateMemeFormData').and.returnValue(formDataMock);
    spyOn(httpClientStub, 'request').and.callThrough();

    service.CreateMeme(dataMock).subscribe(() => { }, () => { }, () => {
      // assert
      expect(service.CreateMemeFormData).toHaveBeenCalledWith(dataMock);

      // asynchronous - je weet niet wanneer 'ie returned
      done();
    });
  });

  it('should get all memes', (done) => {

    spyOn(httpClientStub, 'get').and.callThrough();

    service.GetAllMemes().subscribe((response) => {

      expect(response).not.toBeNull();

      // Complete
      done();
    }, () => {
      done.fail();
     }, () => {
      done();
    })
  });

  it('should fail to get all memes', (done) => {

    spyOn(httpClientStub, 'get').and.returnValue(Observable.throw('Error getting all memes'));

    service.GetAllMemes().subscribe(() => {

      done.fail()

    }, (response) => {
      done();
    }, () => {
      done.fail();
    })
  });
});
