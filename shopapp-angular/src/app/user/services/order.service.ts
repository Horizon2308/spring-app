import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { Observable } from 'rxjs';
import { OrderDTO } from '../dtos/order.dto';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private readonly urlPlaceOrder = `${environment.apiBaseUrl}/orders`;
  constructor(private http: HttpClient) { }

  placeOrder(orderData: OrderDTO): Observable<any> {
    return this.http.post(this.urlPlaceOrder, orderData);
  }
  getOrderById(orderId: number): Observable<any> {
    const url = `${environment.apiBaseUrl}/orders/${orderId}`;
    return this.http.get(url);
  }

  getAllOrders(
    keyword: string,
    page: number,
    limit: number
  ): Observable<any> {
    const params = new HttpParams()
      .set('keyword', keyword)
      .set('page', page.toString())
      .set('limit', limit.toString());
    return this.http.get(`${this.urlPlaceOrder}`, { params });
  }

  deleteOrder(id: number): Observable<any> {
    return this.http.delete(`${this.urlPlaceOrder}/${id}`);
  }

  updateStatus(id: number, new_status: string): Observable<any> {
    return this.http.get(`${this.urlPlaceOrder}/change-status/${id}/${new_status}`);
  }
}
