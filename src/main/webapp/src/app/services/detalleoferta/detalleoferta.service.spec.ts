import { TestBed, inject } from '@angular/core/testing';

import { DetalleofertaService } from './detalleoferta.service';

describe('DetalleofertaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DetalleofertaService]
    });
  });

  it('should be created', inject([DetalleofertaService], (service: DetalleofertaService) => {
    expect(service).toBeTruthy();
  }));
});
