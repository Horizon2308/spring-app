import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/app/user/environments/environment';
import { TransactionDocument } from '../models/transaction.document';
import { TransactionDocumentDetails } from '../models/transaction.document.details';
import { TransactionDocumentDTO } from '../dtos/transaction.document.dto';
import { ExportTransactionDocumentDTO } from '../dtos/export.transaction.document.dto';
import { ExportTransactionDocumentDetails } from '../models/export.transaction.document';

@Injectable({
  providedIn: 'root',
})
export class TransactionDocumentService {
  private readonly urlTransactionDocument = `${environment.apiBaseUrl}/manager/transaction-documents`;

  constructor(private http: HttpClient) {}

  getAllTransactionDocuments(
    sortOption: number,
    keyword: string,
    page: number,
    limit: number
  ): Observable<any> {
    const params = new HttpParams()
      .set('sort', sortOption.toString())
      .set('keyword', keyword)
      .set('page', page.toString())
      .set('limit', limit.toString());
    return this.http.get<TransactionDocument[]>(
      `${this.urlTransactionDocument}/import`,
      { params }
    );
  }

  getAllExportTransactionDocuments(
    sortOption: number,
    keyword: string,
    page: number,
    limit: number
  ): Observable<any> {
    const params = new HttpParams()
      .set('sort', sortOption.toString())
      .set('keyword', keyword)
      .set('page', page.toString())
      .set('limit', limit.toString());
    return this.http.get<TransactionDocument[]>(
      `${this.urlTransactionDocument}/export`,
      { params }
    );
  }

  deleteTransactionDocument(id: number): Observable<any> {
    return this.http.delete(`${this.urlTransactionDocument}/${id}`);
  }

  getTransactionDocumentDetailsByTDId(id: number): Observable<any> {
    return this.http.get<TransactionDocumentDetails[]>(
      `${this.urlTransactionDocument}/details/${id}`
    );
  }

  getExportTransactionDocumentDetailsByTDId(id: number): Observable<any> {
    return this.http.get<ExportTransactionDocumentDetails[]>(
      `${this.urlTransactionDocument}/export/details/${id}`
    );
  }

  addTransactionDocument(
    transactionDocumentDTO: TransactionDocumentDTO
  ): Observable<any> {
    return this.http.post(
      `${this.urlTransactionDocument}/import`,
      transactionDocumentDTO
    );
  }

  addExportTransactionDocument(
    exportTransactionDocumentDTO: ExportTransactionDocumentDTO
  ): Observable<any> {
    return this.http.post(
      `${this.urlTransactionDocument}/export`,
      exportTransactionDocumentDTO
    );
  }
}
