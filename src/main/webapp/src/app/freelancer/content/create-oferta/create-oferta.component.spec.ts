import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateOfertaComponent } from './create-oferta.component';

describe('CreateOfertaComponent', () => {
  let component: CreateOfertaComponent;
  let fixture: ComponentFixture<CreateOfertaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateOfertaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateOfertaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
