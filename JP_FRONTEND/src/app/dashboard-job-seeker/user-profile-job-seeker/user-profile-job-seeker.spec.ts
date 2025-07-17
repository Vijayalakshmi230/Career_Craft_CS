import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserProfileJobSeeker } from './user-profile-job-seeker';

describe('UserProfileJobSeeker', () => {
  let component: UserProfileJobSeeker;
  let fixture: ComponentFixture<UserProfileJobSeeker>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserProfileJobSeeker]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserProfileJobSeeker);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
