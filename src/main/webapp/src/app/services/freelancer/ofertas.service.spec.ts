import { TestBed, inject } from '@angular/core/testing';

import { OfertasFreelancerService } from './ofertas-freelancer.service';

describe('OfertasService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OfertasFreelancerService]
    });
  });

  it('should be created', inject([OfertasFreelancerService], (service: OfertasFreelancerService) => {
    expect(service).toBeTruthy();
  }));
});
