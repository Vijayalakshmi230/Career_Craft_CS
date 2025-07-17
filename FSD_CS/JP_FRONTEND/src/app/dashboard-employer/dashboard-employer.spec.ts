import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardEmployer } from './dashboard-employer';

describe('DashboardEmployer', () => {
  let component: DashboardEmployer;
  let fixture: ComponentFixture<DashboardEmployer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardEmployer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardEmployer);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
