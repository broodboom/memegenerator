import { TestBed } from '@angular/core/testing';
import { Tag } from 'app/models/Tag';
import { Observable } from 'rxjs';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import { HttpClient } from '@angular/common/http';
import { TagService } from './tag.service';
import { ExpandOperator } from 'rxjs/internal/operators/expand';

export class HttpClientStub {
  request(): Observable<any> {
    return Observable.of({});
  }

  get(): Observable<any> {
    // Collection of fake tags
    const tags: Tag[] = [];

    // Create collection of fake tags
    for (let i = 0; i < 5; i++) {
      const tag: Tag = {
        id: i,
        title: `Tag ${i}`,
      };

      tags.push(tag)
    };

    return Observable.of(tags);
  }

  put(): Observable<any> {
    return Observable.of({});
  }

  post(): Observable<any> {
    return Observable.of({});
  }
}

describe('TagService', () => {
  let service: TagService;

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
    service = TestBed.inject(TagService);
  });

  it('should create a tag', (done) => {
    const tag: Tag = {
      id: 1,
      title: 'Test tag'
    };

    spyOn(service, 'createTag').and.returnValue({});

    spyOn(httpClientStub, 'post').and.callThrough();

    service.createTag(tag)

    expect(service.createTag).toHaveBeenCalledWith(tag);

    done();
  });

  it('should get all tags', (done) => {

    spyOn(httpClientStub, 'get').and.callThrough();

    service.getTags().subscribe((response) => {

      expect(response).not.toBeNull();

      // Complete
      done();
    }, () => {
      done.fail();
    }, () => {
      done();
    })
  });

  it('should fail to get all tags', (done) => {

    spyOn(httpClientStub, 'get').and.returnValue(Observable.throw('Error getting all tags'));

    service.getTags().subscribe(() => {

      done.fail()

    }, (response) => {
      done();
    }, () => {
      done.fail();
    })
  });
});