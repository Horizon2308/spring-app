import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionDocumentDetailComponent } from './transaction-document-detail.component';

describe('TransactionDocumentDetailComponent', () => {
  let component: TransactionDocumentDetailComponent;
  let fixture: ComponentFixture<TransactionDocumentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransactionDocumentDetailComponent]
    });
    fixture = TestBed.createComponent(TransactionDocumentDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
