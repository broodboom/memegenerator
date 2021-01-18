import { TestBed } from '@angular/core/testing';
import { User } from 'app/models/User';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import { Observable } from 'rxjs';
import { ProfileService } from './profile.service';

export class HttpClientStub {
  request(): Observable<any> {
    return Observable.of({});
  }

  get(): Observable<any> {
    // Fake user
    const user: User = {
      username: 'test',
      password: 'test',
      email: 'test@test.com'
    };

    return Observable.of(user);
  }

  put(): Observable<any> {
    return Observable.of({});
  }
}

describe('ProfileService', () => {
  let service: ProfileService;

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
    service = TestBed.inject(ProfileService);
  });

  // it('should update a profile', (done) => {

  //   const user: User = {
  //     username: 'test',
  //     password: 'test',
  //     email: 'test@mail.com'
  //   };

  //   spyOn(service, 'updateUserInfo').and.returnValue({})

  //   spyOn(httpClientStub, 'put').and.callThrough();

  //   service.updateUserInfo(user)

  //   expect(service.updateUserInfo(user)).toHaveBeenCalledWith(user)

  //   done();
  // })

  it('should update an user', (done) => {
    const user: User = {
      username: 'test',
      password: 'test',
      email: 'test@test.com'
    };

    spyOn(service, 'updateUserInfo').and.returnValue({});

    spyOn(httpClientStub, 'put').and.callThrough();

    service.updateUserInfo(user)

    expect(service.updateUserInfo).toHaveBeenCalledWith(user);

    done();
  });
});
