import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportTransactionDocumentComponent } from './export-transaction-document.component';

describe('ExportTransactionDocumentComponent', () => {
  let component: ExportTransactionDocumentComponent;
  let fixture: ComponentFixture<ExportTransactionDocumentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExportTransactionDocumentComponent]
    });
    fixture = TestBed.createComponent(ExportTransactionDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
