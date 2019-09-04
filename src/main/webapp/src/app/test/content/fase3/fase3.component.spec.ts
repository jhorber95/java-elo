import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Fase3Component } from './fase3.component';

describe('DetalleofertaComponent', () => {
  let component: Fase3Component;
  let fixture: ComponentFixture<Fase3Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Fase3Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Fase3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
