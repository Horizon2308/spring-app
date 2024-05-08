import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/app/user/environments/environment';
import { ProviderDTO } from '../dtos/provider.dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProviderService {
  private urlProvider = `${environment.apiBaseUrl}/providers`;

  constructor(private http: HttpClient) {}

  addProvider(providerDTO: ProviderDTO): Observable<any> {
    return this.http.post(this.urlProvider, providerDTO);
  }

  getAllProviders(): Observable<any> {
    return this.http.get(this.urlProvider);
  }
}
