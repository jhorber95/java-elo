import { TestBed, inject } from '@angular/core/testing';

import { TipoofreceService } from './tipoofrece.service';

describe('TipoofreceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TipoofreceService]
    });
  });

  it('should be created', inject([TipoofreceService], (service: TipoofreceService) => {
    expect(service).toBeTruthy();
  }));
});
