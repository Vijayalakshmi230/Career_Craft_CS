import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMyJobs } from './view-my-jobs';

describe('ViewMyJobs', () => {
  let component: ViewMyJobs;
  let fixture: ComponentFixture<ViewMyJobs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewMyJobs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewMyJobs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
