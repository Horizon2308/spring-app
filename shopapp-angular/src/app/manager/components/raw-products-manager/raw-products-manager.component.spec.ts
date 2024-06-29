import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RawProductsManagerComponent } from './raw-products-manager.component';

describe('RawProductsManagerComponent', () => {
  let component: RawProductsManagerComponent;
  let fixture: ComponentFixture<RawProductsManagerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RawProductsManagerComponent]
    });
    fixture = TestBed.createComponent(RawProductsManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
