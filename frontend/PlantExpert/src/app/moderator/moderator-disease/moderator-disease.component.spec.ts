import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModeratorDiseaseComponent } from './moderator-disease.component';

describe('ModeratorDiseaseComponent', () => {
  let component: ModeratorDiseaseComponent;
  let fixture: ComponentFixture<ModeratorDiseaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModeratorDiseaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeratorDiseaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
