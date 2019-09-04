import { TestBed, inject } from '@angular/core/testing';

import { WorkflowtestService } from './workflowtest.service';

describe('WorkflowtestService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [WorkflowtestService]
    });
  });

  it('should be created', inject([WorkflowtestService], (service: WorkflowtestService) => {
    expect(service).toBeTruthy();
  }));
});
