import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ModeratorDiseaseEditFormComponent } from './moderator-disease-edit-form.component';

describe('ModeratorDiseaseEditFormComponent', () => {
  let component: ModeratorDiseaseEditFormComponent;
  let fixture: ComponentFixture<ModeratorDiseaseEditFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ModeratorDiseaseEditFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeratorDiseaseEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
