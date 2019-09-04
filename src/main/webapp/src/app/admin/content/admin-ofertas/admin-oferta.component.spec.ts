import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOfertaComponent } from './admin-oferta.component';

describe('AdminOfertaComponent', () => {
  let component: AdminOfertaComponent;
  let fixture: ComponentFixture<AdminOfertaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminOfertaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminOfertaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
