import { TestBed, inject } from '@angular/core/testing';

import { DetallefinancieraService } from './detallefinanciera.service';

describe('DetalleofertaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DetallefinancieraService]
    });
  });

  it('should be created', inject([DetallefinancieraService], (service: DetallefinancieraService) => {
    expect(service).toBeTruthy();
  }));
});
