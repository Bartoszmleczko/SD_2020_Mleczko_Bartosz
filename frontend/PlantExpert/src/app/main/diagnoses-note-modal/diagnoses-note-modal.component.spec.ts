import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DiagnosesNoteModalComponent } from './diagnoses-note-modal.component';

describe('DiagnosesNoteModalComponent', () => {
  let component: DiagnosesNoteModalComponent;
  let fixture: ComponentFixture<DiagnosesNoteModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DiagnosesNoteModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiagnosesNoteModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
