import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  private readonly urlBase = 'http://localhost:8088/api/v1/payment/vn-pay';

  constructor(private http: HttpClient) {}

  vnPay(amount: number): Observable<any> {
    let bankCode = 'NCB';
    const params = new HttpParams()
      .set('amount', amount.toString())
      .set('bankCode', bankCode);
    return this.http.get(this.urlBase, { params });
  }
}
