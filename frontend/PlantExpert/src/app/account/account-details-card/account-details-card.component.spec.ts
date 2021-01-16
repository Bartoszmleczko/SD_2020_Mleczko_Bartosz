import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AccountDetailsCardComponent } from './account-details-card.component';

describe('AccountDetailsCardComponent', () => {
  let component: AccountDetailsCardComponent;
  let fixture: ComponentFixture<AccountDetailsCardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountDetailsCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountDetailsCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
