import { TestBed, inject } from '@angular/core/testing';

import { FormDataTestService } from './form-data-test.service';

describe('FormDataTestService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FormDataTestService]
    });
  });

  it('should be created', inject([FormDataTestService], (service: FormDataTestService) => {
    expect(service).toBeTruthy();
  }));
});
