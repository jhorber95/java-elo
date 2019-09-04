import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GuiavocacionalComponent } from './guiavocacional.component';

describe('CourseComponent', () => {
  let component: GuiavocacionalComponent;
  let fixture: ComponentFixture<GuiavocacionalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GuiavocacionalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GuiavocacionalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
