import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllJobs } from './view-all-jobs';

describe('ViewAllJobs', () => {
  let component: ViewAllJobs;
  let fixture: ComponentFixture<ViewAllJobs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewAllJobs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAllJobs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
