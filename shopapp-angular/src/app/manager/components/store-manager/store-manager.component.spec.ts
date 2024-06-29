import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoreManagerComponent } from './store-manager.component';

describe('StoreManagerComponent', () => {
  let component: StoreManagerComponent;
  let fixture: ComponentFixture<StoreManagerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StoreManagerComponent]
    });
    fixture = TestBed.createComponent(StoreManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
