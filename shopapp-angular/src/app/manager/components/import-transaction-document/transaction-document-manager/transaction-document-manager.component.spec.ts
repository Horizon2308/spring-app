import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionDocumentManagerComponent } from './transaction-document-manager.component';

describe('TransactionDocumentManagerComponent', () => {
  let component: TransactionDocumentManagerComponent;
  let fixture: ComponentFixture<TransactionDocumentManagerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransactionDocumentManagerComponent]
    });
    fixture = TestBed.createComponent(TransactionDocumentManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
