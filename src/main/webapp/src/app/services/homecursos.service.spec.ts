import { TestBed, inject } from '@angular/core/testing';

import { HomecursosService } from './homecursos.service';

describe('HomecursosService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HomecursosService]
    });
  });

  it('should be created', inject([HomecursosService], (service: HomecursosService) => {
    expect(service).toBeTruthy();
  }));
});
