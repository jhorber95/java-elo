import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfilInstiComponent } from './perfil-insti.component';

describe('PerfilInstiComponent', () => {
  let component: PerfilInstiComponent;
  let fixture: ComponentFixture<PerfilInstiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PerfilInstiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfilInstiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
