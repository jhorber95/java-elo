import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewOfertaComponent } from './view-oferta.component';

describe('ViewOfertaComponent', () => {
  let component: ViewOfertaComponent;
  let fixture: ComponentFixture<ViewOfertaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewOfertaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewOfertaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
