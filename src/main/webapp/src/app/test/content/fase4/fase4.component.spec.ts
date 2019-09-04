import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Fase4Component } from './fase4.component';

describe('DetalleofertaComponent', () => {
  let component: Fase4Component;
  let fixture: ComponentFixture<Fase4Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Fase4Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Fase4Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
