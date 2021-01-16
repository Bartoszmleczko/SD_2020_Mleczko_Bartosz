import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AdminDiseaseRefuseComponent } from './admin-disease-refuse.component';

describe('AdminDiseaseRefuseComponent', () => {
  let component: AdminDiseaseRefuseComponent;
  let fixture: ComponentFixture<AdminDiseaseRefuseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminDiseaseRefuseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDiseaseRefuseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
