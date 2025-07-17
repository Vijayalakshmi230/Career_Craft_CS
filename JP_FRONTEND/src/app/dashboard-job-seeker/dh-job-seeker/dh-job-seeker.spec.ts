import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DhJobSeeker } from './dh-job-seeker';

describe('DhJobSeeker', () => {
  let component: DhJobSeeker;
  let fixture: ComponentFixture<DhJobSeeker>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DhJobSeeker]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DhJobSeeker);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
