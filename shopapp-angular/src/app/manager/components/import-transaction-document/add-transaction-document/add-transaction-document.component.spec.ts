import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTransactionDocumentComponent } from './add-transaction-document.component';

describe('AddTransactionDocumentComponent', () => {
  let component: AddTransactionDocumentComponent;
  let fixture: ComponentFixture<AddTransactionDocumentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddTransactionDocumentComponent]
    });
    fixture = TestBed.createComponent(AddTransactionDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
