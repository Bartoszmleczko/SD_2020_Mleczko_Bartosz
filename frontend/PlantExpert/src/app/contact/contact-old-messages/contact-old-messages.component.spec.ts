import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactOldMessagesComponent } from './contact-old-messages.component';

describe('ContactOldMessagesComponent', () => {
  let component: ContactOldMessagesComponent;
  let fixture: ComponentFixture<ContactOldMessagesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContactOldMessagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContactOldMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
