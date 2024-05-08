import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/app/user/environments/environment';
import { ProductDTO } from '../dtos/product.dto';
import { InsertProductDTO } from '../dtos/insert.product.dto';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private urlProducts = `${environment.apiBaseUrl}/products`;

  constructor(private http: HttpClient) {}

  getAllProducts(
    keyword: string,
    page: number,
    limit: number
  ): Observable<any> {
    const params = new HttpParams()
      .set('keyword', keyword)
      .set('page', page.toString())
      .set('limit', limit.toString());
    return this.http.get<any>(this.urlProducts, { params });
  }

  deleteProduct(id: number): Observable<any> {
    //const params = new HttpParams().set('id', productId);
    return this.http.delete(`${this.urlProducts}/${id}`);
  }
  getProduct(id: number): Observable<any> {
    return this.http.get(`${this.urlProducts}/${id}`);
  }
  updateProduct(id: number, productDTO: ProductDTO): Observable<any> {
    return this.http.put(`${this.urlProducts}/${id}`, productDTO);
  }
  insertProduct(insertProductDTO: InsertProductDTO): Observable<any> {
    // Add a new product
    return this.http.post(`${this.urlProducts}`, insertProductDTO);
  }
  uploadImages(productId: number, files: File[]): Observable<any> {
    const formData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append('listOfFiles', files[i]);
    }
    // Upload images for the specified product id
    return this.http.post(`${this.urlProducts}/uploads/${productId}`, formData);
  }
}
