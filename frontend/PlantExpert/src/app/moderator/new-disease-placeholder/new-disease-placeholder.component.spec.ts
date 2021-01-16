import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NewDiseasePlaceholderComponent } from './new-disease-placeholder.component';

describe('NewDiseasePlaceholderComponent', () => {
  let component: NewDiseasePlaceholderComponent;
  let fixture: ComponentFixture<NewDiseasePlaceholderComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NewDiseasePlaceholderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewDiseasePlaceholderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
