import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleuniversidadComponent } from './detalleuniversidad.component';


describe('DetalleuniversidadComponent', () => {
  let component: DetalleuniversidadComponent;
  let fixture: ComponentFixture<DetalleuniversidadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleuniversidadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleuniversidadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
