import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiagnosePlaceholderComponent } from './diagnose-placeholder.component';

describe('DiagnosePlaceholderComponent', () => {
  let component: DiagnosePlaceholderComponent;
  let fixture: ComponentFixture<DiagnosePlaceholderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiagnosePlaceholderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiagnosePlaceholderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
