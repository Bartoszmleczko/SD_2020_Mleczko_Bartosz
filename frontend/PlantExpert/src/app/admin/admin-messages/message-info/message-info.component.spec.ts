import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageInfoComponent } from './message-info.component';

describe('MessageInfoComponent', () => {
  let component: MessageInfoComponent;
  let fixture: ComponentFixture<MessageInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MessageInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MessageInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
