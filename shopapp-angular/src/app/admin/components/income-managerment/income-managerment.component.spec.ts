import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomeManagermentComponent } from './income-managerment.component';

describe('IncomeManagermentComponent', () => {
  let component: IncomeManagermentComponent;
  let fixture: ComponentFixture<IncomeManagermentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IncomeManagermentComponent]
    });
    fixture = TestBed.createComponent(IncomeManagermentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
