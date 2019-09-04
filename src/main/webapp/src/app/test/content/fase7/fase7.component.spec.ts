import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Fase7Component } from './fase7.component';

describe('DetalleofertaComponent', () => {
  let component: Fase7Component;
  let fixture: ComponentFixture<Fase7Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Fase7Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Fase7Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
