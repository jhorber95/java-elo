import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {AdminCreateInstitucionComponent} from './admin-create-institucion.component';

describe('AdminCreateInstitucionComponent', () => {
  let component: AdminCreateInstitucionComponent;
  let fixture: ComponentFixture<AdminCreateInstitucionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminCreateInstitucionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminCreateInstitucionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
