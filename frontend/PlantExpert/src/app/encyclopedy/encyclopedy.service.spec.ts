import { TestBed } from '@angular/core/testing';

import { EncyclopedyService } from './encyclopedy.service';

describe('EncyclopedyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EncyclopedyService = TestBed.get(EncyclopedyService);
    expect(service).toBeTruthy();
  });
});
