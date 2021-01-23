import { TestBed } from "@angular/core/testing";
import { User } from "app/models/User";
import { Observable } from "rxjs";
import { AuthService, LoginResponse } from "./auth.service";

//TODO: ref naar observable, loginResponse => next

export class HttpClientStub {
  public post(): Observable<Object> {
    return new Observable<Object>();
  }
}

export class ProfileServiceStub {
  public getUserInfo(): Observable<User> {
    return new Observable<User>();
  }
}

describe("AuthService", () => {
  let service: AuthService;

  const httpClientStub = new HttpClientStub();
  const profileServiceStub = new ProfileServiceStub();

  let loginResponse: LoginResponse = { status: false };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: ProfileServiceStub, useValue: profileServiceStub },
        { provide: HttpClientStub, useValue: httpClientStub }
      ]
    });
    service = TestBed.inject(AuthService);
  });
});
