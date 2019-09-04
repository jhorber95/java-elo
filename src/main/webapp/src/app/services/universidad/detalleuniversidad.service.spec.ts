import { TestBed, inject } from '@angular/core/testing';

import { DetalleuniversidadService } from './detalleuniversidad.service';

describe('DetalleuniversidadService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DetalleuniversidadService]
    });
  });

  it('should be created', inject([DetalleuniversidadService], (service: DetalleuniversidadService) => {
    expect(service).toBeTruthy();
  }));
});
