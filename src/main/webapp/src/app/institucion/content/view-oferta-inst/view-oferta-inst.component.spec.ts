import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewOfertaInstComponent } from './view-oferta-inst.component';

describe('ViewOfertaInstComponent', () => {
  let component: ViewOfertaInstComponent;
  let fixture: ComponentFixture<ViewOfertaInstComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewOfertaInstComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewOfertaInstComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
