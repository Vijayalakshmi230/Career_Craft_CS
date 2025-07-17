import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardJobSeeker } from './dashboard-job-seeker';

describe('DashboardJobSeeker', () => {
  let component: DashboardJobSeeker;
  let fixture: ComponentFixture<DashboardJobSeeker>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardJobSeeker]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardJobSeeker);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
