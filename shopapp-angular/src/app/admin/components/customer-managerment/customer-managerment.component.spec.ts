import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerManagermentComponent } from './customer-managerment.component';

describe('CustomerManagermentComponent', () => {
  let component: CustomerManagermentComponent;
  let fixture: ComponentFixture<CustomerManagermentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomerManagermentComponent]
    });
    fixture = TestBed.createComponent(CustomerManagermentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
