import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Fase1Component } from './fase1.component';

describe('DetalleofertaComponent', () => {
  let component: Fase1Component;
  let fixture: ComponentFixture<Fase1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Fase1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Fase1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
