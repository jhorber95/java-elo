import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleofertaComponent } from './detalleoferta.component';

describe('DetalleofertaComponent', () => {
  let component: DetalleofertaComponent;
  let fixture: ComponentFixture<DetalleofertaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleofertaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleofertaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
