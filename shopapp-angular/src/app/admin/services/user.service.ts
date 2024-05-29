import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/app/user/environments/environment';
import { UserDTO } from '../dtos/user.dto';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly urlUser = `${environment.apiBaseUrl}/users`;

  constructor(private http: HttpClient) {}

  deleteStaff(id: number): Observable<any> {
    return this.http.delete(`${this.urlUser}/${id}`);
  }
}
