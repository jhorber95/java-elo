import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminInstitucionesComponent } from './admin-instituciones.component';


describe('AdminInstitucionesComponent', () => {
  let component: AdminInstitucionesComponent;
  let fixture: ComponentFixture<AdminInstitucionesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminInstitucionesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminInstitucionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
