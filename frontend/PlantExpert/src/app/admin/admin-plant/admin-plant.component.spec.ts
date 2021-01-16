import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AdminPlantComponent } from './admin-plant.component';

describe('AdminPlantComponent', () => {
  let component: AdminPlantComponent;
  let fixture: ComponentFixture<AdminPlantComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminPlantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPlantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
