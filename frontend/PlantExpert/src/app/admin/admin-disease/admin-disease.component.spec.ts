import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AdminDiseaseComponent } from './admin-disease.component';

describe('AdminDiseaseComponent', () => {
  let component: AdminDiseaseComponent;
  let fixture: ComponentFixture<AdminDiseaseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminDiseaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDiseaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
