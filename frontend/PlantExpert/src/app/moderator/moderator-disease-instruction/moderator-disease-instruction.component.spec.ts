import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModeratorDiseaseInstructionComponent } from './moderator-disease-instruction.component';

describe('ModeratorDiseaseInstructionComponent', () => {
  let component: ModeratorDiseaseInstructionComponent;
  let fixture: ComponentFixture<ModeratorDiseaseInstructionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModeratorDiseaseInstructionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeratorDiseaseInstructionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
