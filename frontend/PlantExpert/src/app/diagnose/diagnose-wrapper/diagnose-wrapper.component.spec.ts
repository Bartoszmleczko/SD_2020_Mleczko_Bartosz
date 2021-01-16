import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DiagnoseWrapperComponent } from './diagnose-wrapper.component';

describe('DiagnoseWrapperComponent', () => {
  let component: DiagnoseWrapperComponent;
  let fixture: ComponentFixture<DiagnoseWrapperComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DiagnoseWrapperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiagnoseWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
