import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewDiseasePlaceholderComponent } from './new-disease-placeholder.component';

describe('NewDiseasePlaceholderComponent', () => {
  let component: NewDiseasePlaceholderComponent;
  let fixture: ComponentFixture<NewDiseasePlaceholderComponent>;

  beforeEach(async(() => {
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
