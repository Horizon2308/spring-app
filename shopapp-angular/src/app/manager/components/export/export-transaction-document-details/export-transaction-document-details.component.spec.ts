import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportTransactionDocumentDetailsComponent } from './export-transaction-document-details.component';

describe('ExportTransactionDocumentDetailsComponent', () => {
  let component: ExportTransactionDocumentDetailsComponent;
  let fixture: ComponentFixture<ExportTransactionDocumentDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExportTransactionDocumentDetailsComponent]
    });
    fixture = TestBed.createComponent(ExportTransactionDocumentDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
