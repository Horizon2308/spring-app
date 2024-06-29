import { NumberSymbol } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/app/user/environments/environment';
import { RawProduct } from '../models/raw.product';

@Injectable({
  providedIn: 'root',
})
export class RawProductService {
  private readonly urlRawProduct = `${environment.apiBaseUrl}/manager/raw-products`;

  constructor(private http: HttpClient) {}

  getAllRawProducts(
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
    return this.http.get<RawProduct[]>(this.urlRawProduct, { params });
  }

  searchRawProducts(keyword: string): Observable<any> {
    const params = new HttpParams().set('keyword', keyword);
    return this.http.get(`${this.urlRawProduct}/search`, { params });
  }
}
