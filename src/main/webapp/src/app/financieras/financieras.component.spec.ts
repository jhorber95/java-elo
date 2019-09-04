import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinancierasComponent } from './financieras.component';

describe('FinancierasComponent', () => {
  let component: FinancierasComponent;
  let fixture: ComponentFixture<FinancierasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinancierasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinancierasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
