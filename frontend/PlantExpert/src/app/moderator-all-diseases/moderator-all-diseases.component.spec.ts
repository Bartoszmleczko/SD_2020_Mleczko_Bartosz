import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ModeratorAllDiseasesComponent } from './moderator-all-diseases.component';

describe('ModeratorAllDiseasesComponent', () => {
  let component: ModeratorAllDiseasesComponent;
  let fixture: ComponentFixture<ModeratorAllDiseasesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ModeratorAllDiseasesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeratorAllDiseasesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
