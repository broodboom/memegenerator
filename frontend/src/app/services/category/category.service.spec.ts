import { TestBed } from '@angular/core/testing';
import { Category } from 'app/models/Category'
import { CategoryService } from './category.service';
import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { environment } from "environments/environment";

describe('CategoryService', () => {
  let service: CategoryService;

  let httpMock : HttpTestingController;

  let httpClient : HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(CategoryService);
    httpMock = TestBed.get(HttpTestingController);
    httpClient = TestBed.get(HttpClient);
  });

  // Test fetching all
  it('should get all categories', () => {

    const fakeCategories : Category[] = []

    for(let i = 0; i < 5; i ++){
      const category: Category = {
        title: `Test category ${i}`
      };

      fakeCategories.push(category)
    }

    service.getCategories().subscribe((c)=>{
      expect(c).toEqual(fakeCategories)
    });
     
    const request = httpMock.expectOne(`${environment.apiUrl}/category/`);

    expect(request.request.method).toEqual('GET');

    request.flush(fakeCategories);

    httpMock.verify();
  });
});