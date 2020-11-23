import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiagnosesTableComponent } from './diagnoses-table.component';

describe('DiagnosesTableComponent', () => {
  let component: DiagnosesTableComponent;
  let fixture: ComponentFixture<DiagnosesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiagnosesTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiagnosesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
