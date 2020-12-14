import { TestBed } from '@angular/core/testing';
import { Meme } from 'app/models/Meme';

import { MemeService } from './meme.service';

describe('MemeService', () => {
  let service: MemeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MemeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return a formdata object', () => {
    const memeId = 0;
    const memeTitle = 'abc';
    const memeImage = new Blob();

    const dataMock = new Meme(memeTitle, memeImage, memeId);

    const result = service.CreateMemeFormData(dataMock);

    expect(result.get("title")).toEqual(memeTitle);
  });
});
