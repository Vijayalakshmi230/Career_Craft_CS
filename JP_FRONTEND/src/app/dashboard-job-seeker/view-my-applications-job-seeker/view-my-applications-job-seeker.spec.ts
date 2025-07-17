import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMyApplicationsJobSeeker } from './view-my-applications-job-seeker';

describe('ViewMyApplicationsJobSeeker', () => {
  let component: ViewMyApplicationsJobSeeker;
  let fixture: ComponentFixture<ViewMyApplicationsJobSeeker>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewMyApplicationsJobSeeker]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewMyApplicationsJobSeeker);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
