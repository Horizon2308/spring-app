import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StaffManagermentComponent } from './staff-managerment.component';

describe('StaffManagermentComponent', () => {
  let component: StaffManagermentComponent;
  let fixture: ComponentFixture<StaffManagermentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StaffManagermentComponent]
    });
    fixture = TestBed.createComponent(StaffManagermentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
