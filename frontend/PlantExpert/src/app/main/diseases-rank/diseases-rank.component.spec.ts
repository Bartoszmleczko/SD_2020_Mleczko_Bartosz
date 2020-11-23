import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiseasesRankComponent } from './diseases-rank.component';

describe('DiseasesRankComponent', () => {
  let component: DiseasesRankComponent;
  let fixture: ComponentFixture<DiseasesRankComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiseasesRankComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiseasesRankComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
