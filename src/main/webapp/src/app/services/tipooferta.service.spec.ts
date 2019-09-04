import { TestBed, inject } from '@angular/core/testing';

import { TipoofertaService } from './tipooferta.service';

describe('TipoofertaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TipoofertaService]
    });
  });

  it('should be created', inject([TipoofertaService], (service: TipoofertaService) => {
    expect(service).toBeTruthy();
  }));
});
