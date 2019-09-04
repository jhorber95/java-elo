import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Fase2Component } from './fase2.component';

describe('DetalleofertaComponent', () => {
  let component: Fase2Component;
  let fixture: ComponentFixture<Fase2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Fase2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Fase2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
