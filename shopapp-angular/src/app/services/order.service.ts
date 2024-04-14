import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { Observable } from 'rxjs';
import { OrderDTO } from '../dtos/order.dto';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
    private readonly urlPlaceOrder = `${environment.apiBaseUrl}/orders`
    constructor(private http: HttpClient) { }
    
    placeOrder(orderData: OrderDTO): Observable<any> {
        return this.http.post(this.urlPlaceOrder, orderData);
    }
    
}
