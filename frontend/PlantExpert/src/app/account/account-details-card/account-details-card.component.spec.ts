import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountDetailsCardComponent } from './account-details-card.component';

describe('AccountDetailsCardComponent', () => {
  let component: AccountDetailsCardComponent;
  let fixture: ComponentFixture<AccountDetailsCardComponent>;

  beforeEach(async(() => {
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
