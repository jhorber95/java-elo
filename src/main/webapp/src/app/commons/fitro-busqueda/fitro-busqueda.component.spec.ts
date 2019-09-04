import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FitroBusquedaComponent } from './fitro-busqueda.component';

describe('FitroBusquedaComponent', () => {
  let component: FitroBusquedaComponent;
  let fixture: ComponentFixture<FitroBusquedaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FitroBusquedaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FitroBusquedaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
