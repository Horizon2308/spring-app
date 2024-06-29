import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/app/user/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class StatisticService {
  private readonly baseUrl = `${environment.apiBaseUrl}`;
  constructor(private http: HttpClient) {}

  countCustomers(): Observable<any> {
    return this.http.get(`${this.baseUrl}/users/count/customers`);
  }

  countProducts(): Observable<any> {
    return this.http.get(`${this.baseUrl}/products/count`);
  }

  countOrders(): Observable<any> {
    return this.http.get(`${this.baseUrl}/orders/count`);
  }

  countProductsSoldOut(): Observable<any> {
    return this.http.get(`${this.baseUrl}/products/count/sout-out`);
  }

  getLatestOrders(): Observable<any> {
    return this.http.get(`${this.baseUrl}/orders/get-latest-orders`);
  }

  getLatestCustomers(): Observable<any> {
    return this.http.get(`${this.baseUrl}/users/count/get-latest-customers`);
  }

  countStaffs(): Observable<any> {
    return this.http.get(`${this.baseUrl}/users/count/staffs`);
  }

  getTotalMoney(): Observable<any> {
    return this.http.get(`${this.baseUrl}/orders/count/get-total-money`);
  }
  countCancelledOrder(): Observable<any> {
    return this.http.get(`${this.baseUrl}/orders/count/cancelled-order`);
  }
  getPopularProducts(): Observable<any> {
    return this.http.get(`${this.baseUrl}/products/statistic/get-popular-products`);
  }

  getSuccessfulOrders(): Observable<any> {
    return this.http.get(
      `${this.baseUrl}/orders/statistic/get-successful-orders`
    );
  }

  getProductsIsSoldOut(): Observable<any> {
    return this.http.get(
      `${this.baseUrl}/products/statistic/get-products-is-sold-out`
    );
  }
}
