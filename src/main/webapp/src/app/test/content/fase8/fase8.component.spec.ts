import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Fase8Component } from './fase8.component';

describe('DetalleofertaComponent', () => {
  let component: Fase8Component;
  let fixture: ComponentFixture<Fase8Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Fase8Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Fase8Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
