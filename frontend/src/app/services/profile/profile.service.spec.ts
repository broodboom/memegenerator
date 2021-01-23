import { TestBed } from '@angular/core/testing';
import { User } from 'app/models/User';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import { Observable } from 'rxjs';
import { ProfileService } from './profile.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { environment } from "environments/environment";

describe('ProfileService', () => {
  let service: ProfileService;

  let httpMock: HttpTestingController;

  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(ProfileService);
    httpMock = TestBed.get(HttpTestingController);
    httpClient = TestBed.get(HttpClient);
  });

  it('should update the profile', () => {
    const profile: User = {
      username: 'test',
      password: 'test',
      email: 'test@test.com'
    };

    service.updateUserInfo(profile).subscribe((p) => {
      expect(p).not.toEqual(profile)
    })

    const request = httpMock.expectOne(`${environment.apiUrl}/user`);

    expect(request.request.method).toEqual('PUT');

    const updatedUser : User = {
      username: 'test 2',
      password:  'test 2',
      email: 'test2@test.com'
    }

    request.flush(updatedUser);

    httpMock.verify();
  })
});
