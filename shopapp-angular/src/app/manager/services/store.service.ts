import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/app/user/environments/environment';
import { Store } from '../models/store';
import { StoreDTO } from '../dtos/store.dto';

@Injectable({
  providedIn: 'root',
})
export class StoreService {
  private readonly urlStore = `${environment.apiBaseUrl}/manager/stores`;

  constructor(private http: HttpClient) {}

  getAllStores(
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
    return this.http.get<Store[]>(this.urlStore, { params });
  }

  createStore(storeDTO: StoreDTO): Observable<any> {
    return this.http.post(this.urlStore, storeDTO);
  }

  deleteStore(id: number): Observable<any> {
    return this.http.delete(`${this.urlStore}/${id}`);
  }

  updateStore(id: number, storeDTO: StoreDTO): Observable<any> {
    return this.http.put(`${this.urlStore}/${id}`, storeDTO);
  }

  getAllStore(): Observable<any> {
    return this.http.get<Store[]>(`${this.urlStore}/without-pages`);
  }
}
