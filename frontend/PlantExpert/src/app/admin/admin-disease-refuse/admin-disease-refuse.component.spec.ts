import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDiseaseRefuseComponent } from './admin-disease-refuse.component';

describe('AdminDiseaseRefuseComponent', () => {
  let component: AdminDiseaseRefuseComponent;
  let fixture: ComponentFixture<AdminDiseaseRefuseComponent>;

  beforeEach(async(() => {
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
