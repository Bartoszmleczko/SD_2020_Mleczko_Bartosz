import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ModeratorPlantComponent } from './moderator-plant.component';

describe('ModeratorPlantComponent', () => {
  let component: ModeratorPlantComponent;
  let fixture: ComponentFixture<ModeratorPlantComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ModeratorPlantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeratorPlantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
