import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GHNService {
  private readonly tokenGHN = '7f1e9c93-f95e-11ee-8d6a-5276b526e4bb';

  private readonly shopId = '5012236';

  private readonly shopAddress = '1542';

  private readonly urlApiGetProvince =
    'https://online-gateway.ghn.vn/shiip/public-api/master-data/province';

  private readonly urlApiGetDistrict =
    'https://online-gateway.ghn.vn/shiip/public-api/master-data/district';

  private readonly urlApiGetWard =
    'https://online-gateway.ghn.vn/shiip/public-api/master-data/ward';

  private readonly urlApiGetMethodServices =
    'https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services';

  private readonly urlApiGetFee =
    'https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee';

  private readonly urlApiGetShippingDate =
    'https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/leadtime';

  constructor(private http: HttpClient) {}

  getProvince(): Observable<any> {
    const headers = new HttpHeaders().set('token', this.tokenGHN);
    return this.http.get<any>(this.urlApiGetProvince, { headers });
  }

  async getProvinceAsync(): Promise<Observable<any>> {
    const headers = new HttpHeaders().set('token', this.tokenGHN);
    return this.http.get<any>(this.urlApiGetProvince, { headers });
  }

  getDistrict(province_id: number): Observable<any> {
    const headers = new HttpHeaders().set('token', this.tokenGHN);
    debugger;
    return this.http.post<any>(
      this.urlApiGetDistrict,
      { province_id },
      {
        headers,
      }
    );
  }

  getWard(district_id: number): Observable<any> {
    const headers = new HttpHeaders().set('token', this.tokenGHN);
    return this.http.post<any>(
      this.urlApiGetWard,
      { district_id },
      { headers }
    );
  }

  getMethodServices(district_id: number): Observable<any> {
    const params = new HttpParams()
      .set('from_district', this.shopAddress)
      .set('shop_id', this.shopId)
      .set('to_district', district_id);
    const headers = new HttpHeaders().set('token', this.tokenGHN);
    return this.http.get<any>(this.urlApiGetMethodServices, {
      headers,
      params,
    });
  }

  getFee(
    service_id: number,
    insurance_value: number,
    to_ward_code: string,
    to_district_id: number
  ): Observable<any> {
    const params = new HttpParams()
      .set('service_id', service_id)
      .set('insurance_value', insurance_value)
      .set('to_ward_code', to_ward_code)
      .set('to_district_id', to_district_id)
      .set('from_district_id', this.shopAddress)
      .set('height', 60)
      .set('length', 30)
      .set('weight', 500)
      .set('width', 15);
    const headers = new HttpHeaders().set('token', this.tokenGHN);
    return this.http.get<any>(this.urlApiGetFee, { headers, params });
  }

  getShippingDate(
    districtId: number,
    wardCode: string,
    serviceId: number
  ): Observable<any> {
    const headers = new HttpHeaders()
      .set('token', this.tokenGHN)
      .set('ShopId', this.shopId);
    const params = new HttpParams()
      .set('from_district_id', this.shopAddress)
      .set('to_district_id', districtId)
      .set('to_ward_code', wardCode)
      .set('service_id', serviceId);
    return this.http.get<any>(this.urlApiGetShippingDate, { headers, params });
  }
}
