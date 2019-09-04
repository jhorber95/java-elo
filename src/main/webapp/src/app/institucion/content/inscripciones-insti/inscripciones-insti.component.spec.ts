import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InscripcionesInstiComponent } from './inscripciones-insti.component';

describe('InscripcionesInstiComponent', () => {
  let component: InscripcionesInstiComponent;
  let fixture: ComponentFixture<InscripcionesInstiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InscripcionesInstiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InscripcionesInstiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
