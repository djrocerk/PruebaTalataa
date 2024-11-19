import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Order } from 'src/app/core/models/order.interface';
import { environment } from 'src/environment/enviroment';
import { LoginService } from '../../login/service/login.service';

@Injectable({
  providedIn: 'root',
})
export class OrdenService {
  constructor(private authService: LoginService,private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getuserToken();
    return new HttpHeaders({
      'Authorization': token ? `Bearer ${token}` : '',
      'Content-Type': 'application/json'
    });
  }
  getOrdenes(): Observable<Order[]> {
    const url = `${environment.api}/orden`;
    const headers = this.getAuthHeaders();
    return this.http.get<Order[]>(url,{ headers });
  }

  postOrden(orderItems: any[]): Observable<any> {
    const url = `${environment.api}/orden`;
    const headers = this.getAuthHeaders();
    return this.http.post(url, orderItems, { headers });
  }

  deleteOrden(id: number) {
    const url = `${environment.api}/orden/${id}`;
    const headers = this.getAuthHeaders();
    return this.http.delete(url, { headers });
  }
}
