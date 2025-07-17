import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostJobs } from './post-jobs';

describe('PostJobs', () => {
  let component: PostJobs;
  let fixture: ComponentFixture<PostJobs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PostJobs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostJobs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
