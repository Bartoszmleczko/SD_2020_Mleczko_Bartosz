import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AccountPasswordComponent } from './account-password.component';

describe('AccountPasswordComponent', () => {
  let component: AccountPasswordComponent;
  let fixture: ComponentFixture<AccountPasswordComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountPasswordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
