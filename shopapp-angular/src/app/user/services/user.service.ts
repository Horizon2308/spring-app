import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { RegisterDTO } from '../dtos/register.dto';
import { LoginDTO } from '../dtos/login.dto';
import { environment } from '../environments/environment';
import { UserResponse } from '../../responses/user/user.response';
import { UpdateUserDTO } from '../dtos/update.user.dto';
import { UserDTO } from 'src/app/admin/dtos/user.dto';
import { InsertStaffDTO } from 'src/app/admin/dtos/insert.staff.dto';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private urlRegister = `${environment.apiBaseUrl}/users/register`;

  private urlLogin = `${environment.apiBaseUrl}/users/login`;

  private apiUserDetail = `${environment.apiBaseUrl}/users/details`;

  private urlUsers = `${environment.apiBaseUrl}/users`;

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

  getUserDetail(token: string) {
    return this.http.post(this.apiUserDetail, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    });
  }

  saveUserResponseToLocalStorage(userResponse?: UserResponse) {
    try {
      debugger;
      if (userResponse == null || !userResponse) {
        return;
      }
      // Convert the userResponse object to a JSON string
      const userResponseJSON = JSON.stringify(userResponse);
      // Save the JSON string to local storage with a key (e.g., "userResponse")
      localStorage.setItem('user', userResponseJSON);
      console.log('User response saved to local storage.');
    } catch (error) {
      console.error('Error saving user response to local storage:', error);
    }
  }
  getUserResponseFromLocalStorage(): UserResponse | null {
    try {
      // Retrieve the JSON string from local storage using the key
      const userResponseJSON = localStorage.getItem('user');
      if (userResponseJSON == null || userResponseJSON == undefined) {
        return null;
      }
      // Parse the JSON string back to an object
      const userResponse = JSON.parse(userResponseJSON!);
      console.log('User response retrieved from local storage.');
      return userResponse;
    } catch (error) {
      console.error(
        'Error retrieving user response from local storage:',
        error
      );
      return null; // Return null or handle the error as needed
    }
  }
  removeUserFromLocalStorage(): void {
    try {
      // Remove the user data from local storage using the key
      localStorage.removeItem('user');
      console.log('User data removed from local storage.');
    } catch (error) {
      console.error('Error removing user data from local storage:', error);
      // Handle the error as needed
    }
  }

  updateUserDetail(token: string, updateUserDTO: UpdateUserDTO) {
    debugger;
    let userResponse = this.getUserResponseFromLocalStorage();
    return this.http.put(
      `${this.apiUserDetail}/${userResponse?.id}`,
      updateUserDTO,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        }),
      }
    );
  }

  getAllStaffs(keyword: string, page: number, limit: number): Observable<any> {
    const params = new HttpParams()
      .set('keyword', keyword)
      .set('page', page.toString())
      .set('limit', limit.toString());
    return this.http.get(`${this.urlUsers}/staffs`, { params });
  }

  getAllCustomers(
    keyword: string,
    page: number,
    limit: number
  ): Observable<any> {
    const params = new HttpParams()
      .set('keyword', keyword)
      .set('page', page.toString())
      .set('limit', limit.toString());
    return this.http.get(`${this.urlUsers}/customers`, { params });
  }
  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.urlUsers}/${id}`);
  }

  getUser(id: number) {
    return this.http.get(`${this.urlUsers}/${id}`);
  }

  updateUser(id: number, userDTO: UserDTO): Observable<any> {
    return this.http.put<any>(`${this.urlUsers}/${id}`, userDTO);
  }

  insertStaff(userDTO: InsertStaffDTO): Observable<any> {
    return this.http.post(this.urlRegister, userDTO, {
      headers: this.getHeaders(),
    });
  }

  uploadImages(id: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    // Upload images for the specified product id
    return this.http.post(`${this.urlUsers}/uploads/${id}`, formData);
  }
}
