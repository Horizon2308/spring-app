import { Injectable } from '@angular/core';
import { environment } from 'src/app/user/environments/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Category } from '../models/category';
import { CategoryDTO } from '../dtos/category.dto';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private http: HttpClient) {}
  private readonly urlCategories = `${environment.apiBaseUrl}/categories`;
  getAllCategories(page: number, limit: number): Observable<any> {
    return this.http.get<Category[]>(this.urlCategories);
  }

  addCategory(categoryDTO: CategoryDTO): Observable<any> {
    return this.http.post(this.urlCategories, categoryDTO);
  }
}
