import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OldMessagesComponent } from './old-messages.component';

describe('OldMessagesComponent', () => {
  let component: OldMessagesComponent;
  let fixture: ComponentFixture<OldMessagesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OldMessagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OldMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
