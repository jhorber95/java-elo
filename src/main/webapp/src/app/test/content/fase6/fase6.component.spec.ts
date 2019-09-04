import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Fase6Component } from './fase6.component';

describe('DetalleofertaComponent', () => {
  let component: Fase6Component;
  let fixture: ComponentFixture<Fase6Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Fase6Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Fase6Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
