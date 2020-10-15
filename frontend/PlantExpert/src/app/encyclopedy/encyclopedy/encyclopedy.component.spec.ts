import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EncyclopedyComponent } from './encyclopedy.component';

describe('EncyclopedyComponent', () => {
  let component: EncyclopedyComponent;
  let fixture: ComponentFixture<EncyclopedyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EncyclopedyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EncyclopedyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
