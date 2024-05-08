import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductManagermentComponent } from './product-managerment.component';

describe('ProductManagermentComponent', () => {
  let component: ProductManagermentComponent;
  let fixture: ComponentFixture<ProductManagermentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductManagermentComponent]
    });
    fixture = TestBed.createComponent(ProductManagermentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
