import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPqrsComponent } from './admin-pqrs.component';

describe('AdminPqrsComponent', () => {
  let component: AdminPqrsComponent;
  let fixture: ComponentFixture<AdminPqrsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminPqrsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPqrsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
