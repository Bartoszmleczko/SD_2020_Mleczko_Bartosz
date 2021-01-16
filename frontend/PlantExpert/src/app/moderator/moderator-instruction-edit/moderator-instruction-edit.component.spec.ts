import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModeratorInstructionEditComponent } from './moderator-instruction-edit.component';

describe('ModeratorInstructionEditComponent', () => {
  let component: ModeratorInstructionEditComponent;
  let fixture: ComponentFixture<ModeratorInstructionEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModeratorInstructionEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeratorInstructionEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
