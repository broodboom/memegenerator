import { TestBed } from '@angular/core/testing';

import { LikebuttonService } from './likebutton.service';

describe('LikebuttonService', () => {
  let service: LikebuttonService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LikebuttonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
