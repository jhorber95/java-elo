import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignupFreelancerComponent } from './signup-freelancer.component';

describe('SignupFreelancerComponent', () => {
  let component: SignupFreelancerComponent;
  let fixture: ComponentFixture<SignupFreelancerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignupFreelancerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignupFreelancerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
