import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environment/enviroment';
import { LoginService } from 'src/app/modules/login/service/login.service';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private apiUrl = `${environment.api}/security`;

  constructor(private http: HttpClient, private authService: LoginService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getuserToken();
    return new HttpHeaders({
      Authorization: token ? `Bearer ${token}` : '',
      'Content-Type': 'application/json',
    });
  }

  registerUser(userData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.apiUrl}/register`, userData, { headers });
  }

  getUsers(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.apiUrl}/consultar-usuarios`, { headers });
  }

  updateUser(id: number, userData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/modificar-usuario/${id}`, userData, { headers });
  }

  deleteUser(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/eliminar-usuario/${id}`, {}, { headers });
  }

}
