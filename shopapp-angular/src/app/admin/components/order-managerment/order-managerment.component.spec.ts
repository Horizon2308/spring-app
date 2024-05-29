import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderManagermentComponent } from './order-managerment.component';

describe('OrderManagermentComponent', () => {
  let component: OrderManagermentComponent;
  let fixture: ComponentFixture<OrderManagermentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrderManagermentComponent]
    });
    fixture = TestBed.createComponent(OrderManagermentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
