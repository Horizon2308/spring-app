import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/app/user/environments/environment';
import { Observable } from 'rxjs';
import { CustomerDTO } from '../dtos/customer.dto';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  private readonly urlBase = `${environment.apiBaseUrl}/customers`;

  constructor(private http: HttpClient) {}

  searchCustomers(keyword: string): Observable<any> {
    const params = new HttpParams().set('keyword', keyword);
    return this.http.get<any>(`${this.urlBase}/search-customers`, {
      params,
    });
  }

  createCustomer(customerDTO: CustomerDTO): Observable<any> {
    return this.http.post(`${this.urlBase}`, customerDTO);
  }
}
