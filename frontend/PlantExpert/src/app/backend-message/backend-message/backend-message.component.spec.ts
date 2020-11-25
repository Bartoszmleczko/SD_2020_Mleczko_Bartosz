import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BackendMessageComponent } from './backend-message.component';

describe('BackendMessageComponent', () => {
  let component: BackendMessageComponent;
  let fixture: ComponentFixture<BackendMessageComponent>;

  beforeEach(async(() => {
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
