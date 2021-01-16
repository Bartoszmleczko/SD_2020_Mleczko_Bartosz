import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BackendMessageComponent } from './backend-message.component';

describe('BackendMessageComponent', () => {
  let component: BackendMessageComponent;
  let fixture: ComponentFixture<BackendMessageComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BackendMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BackendMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
