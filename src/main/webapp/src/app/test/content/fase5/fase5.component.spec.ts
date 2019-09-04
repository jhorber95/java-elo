import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Fase5Component } from './fase5.component';

describe('DetalleofertaComponent', () => {
  let component: Fase5Component;
  let fixture: ComponentFixture<Fase5Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Fase5Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Fase5Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
