import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateOfertaInstComponent } from './create-oferta-inst.component';

describe('CreateOfertaInstComponent', () => {
  let component: CreateOfertaInstComponent;
  let fixture: ComponentFixture<CreateOfertaInstComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateOfertaInstComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateOfertaInstComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
