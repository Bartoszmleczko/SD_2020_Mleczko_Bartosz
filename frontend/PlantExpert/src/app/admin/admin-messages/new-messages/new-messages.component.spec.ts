import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NewMessagesComponent } from './new-messages.component';

describe('NewMessagesComponent', () => {
  let component: NewMessagesComponent;
  let fixture: ComponentFixture<NewMessagesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NewMessagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
