import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfilFreelancerComponent } from './perfil-freelancer.component';

describe('PerfilFreelancerComponent', () => {
  let component: PerfilFreelancerComponent;
  let fixture: ComponentFixture<PerfilFreelancerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PerfilFreelancerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfilFreelancerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
