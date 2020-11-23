import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPlantComponent } from './admin-plant.component';

describe('AdminPlantComponent', () => {
  let component: AdminPlantComponent;
  let fixture: ComponentFixture<AdminPlantComponent>;

  beforeEach(async(() => {
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
