import { TestBed, inject } from '@angular/core/testing';

import { WorkflowtestGuardService } from './workflowtest-guard.service';

describe('WorkflowtestGuardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [WorkflowtestGuardService]
    });
  });

  it('should be created', inject([WorkflowtestGuardService], (service: WorkflowtestGuardService) => {
    expect(service).toBeTruthy();
  }));
});
