import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TransactionDocumentDetails } from 'src/app/manager/models/transaction.document.details';
import { TransactionDocumentService } from 'src/app/manager/services/transaction.document.service';

@Component({
  selector: 'app-transaction-document-detail',
  templateUrl: './transaction-document-detail.component.html',
  styleUrls: ['./transaction-document-detail.component.scss'],
})
export class TransactionDocumentDetailComponent implements OnInit {
  transactionDocumentId: number = 0;
  transactionDocumentDetails: TransactionDocumentDetails[] = [];
  today = new Date();

  day: string = '';
  month: string = '';
  year: number = 0;
  index: number = 0;
  totalPrice: number = 0;

  constructor(
    private transactionDocumentService: TransactionDocumentService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.index = 0;
    this.year = this.today.getFullYear();
    this.month = String(this.today.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
    this.day = String(this.today.getDate()).padStart(2, '0');
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    debugger;
    if (idParam !== null) {
      this.transactionDocumentId = +idParam;
    }
    if (!isNaN(this.transactionDocumentId) && this.transactionDocumentId != 0) {
      this.transactionDocumentService
        .getTransactionDocumentDetailsByTDId(this.transactionDocumentId)
        .subscribe({
          next: (response: any) => {
            debugger;
            this.transactionDocumentDetails = response.data;
            this.getTotalPrice();
          },
          complete: () => {},
          error: (error: any) => {
            console.error(
              'Error fetching transaction document details:',
              error
            );
          },
        });
    }
  }

  getTotalPrice(): void {
    this.transactionDocumentDetails.forEach(
      (transactionDocumentDetail: TransactionDocumentDetails) => {
        this.totalPrice += transactionDocumentDetail.price;
      }
    );
  }
}
