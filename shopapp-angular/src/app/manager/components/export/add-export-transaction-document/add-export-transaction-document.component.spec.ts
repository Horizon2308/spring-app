import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddExportTransactionDocumentComponent } from './add-export-transaction-document.component';

describe('AddExportTransactionDocumentComponent', () => {
  let component: AddExportTransactionDocumentComponent;
  let fixture: ComponentFixture<AddExportTransactionDocumentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddExportTransactionDocumentComponent]
    });
    fixture = TestBed.createComponent(AddExportTransactionDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
