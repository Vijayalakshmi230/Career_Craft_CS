import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SettingsJobSeeker } from './settings-job-seeker';

describe('SettingsJobSeeker', () => {
  let component: SettingsJobSeeker;
  let fixture: ComponentFixture<SettingsJobSeeker>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SettingsJobSeeker]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SettingsJobSeeker);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
