import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostCounterComponent } from './post-counter.component';

describe('PostCounterComponent', () => {
  let component: PostCounterComponent;
  let fixture: ComponentFixture<PostCounterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostCounterComponent]
    });
    fixture = TestBed.createComponent(PostCounterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
