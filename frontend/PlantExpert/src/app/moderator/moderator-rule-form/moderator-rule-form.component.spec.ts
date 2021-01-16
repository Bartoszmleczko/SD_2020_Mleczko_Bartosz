import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ModeratorRuleFormComponent } from './moderator-rule-form.component';

describe('ModeratorRuleFormComponent', () => {
  let component: ModeratorRuleFormComponent;
  let fixture: ComponentFixture<ModeratorRuleFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ModeratorRuleFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeratorRuleFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
