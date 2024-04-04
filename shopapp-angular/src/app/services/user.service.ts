import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { RegisterDTO } from '../dtos/register.dto';
import { LoginDTO } from '../dtos/login.dto';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private urlRegister = `${environment.apiBaseUrl}/users/register`;
  private urlLogin = `${environment.apiBaseUrl}/users/login`;
  constructor(private http: HttpClient) {}
  register(registerDTO: RegisterDTO): Observable<any> {
    return this.http.post(this.urlRegister, registerDTO, {
      headers: this.getHeaders(),
    });
  }
  login(loginDTO: LoginDTO): Observable<any> {
    return this.http.post(this.urlLogin, loginDTO, {
      headers: this.getHeaders(),
    });
  }
  getHeaders(): HttpHeaders {
    return new HttpHeaders({ 'Content-Type': 'application/json' });
  }
}
