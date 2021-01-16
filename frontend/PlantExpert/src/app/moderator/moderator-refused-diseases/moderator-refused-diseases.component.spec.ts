import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ModeratorRefusedDiseasesComponent } from './moderator-refused-diseases.component';

describe('ModeratorRefusedDiseasesComponent', () => {
  let component: ModeratorRefusedDiseasesComponent;
  let fixture: ComponentFixture<ModeratorRefusedDiseasesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ModeratorRefusedDiseasesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeratorRefusedDiseasesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
