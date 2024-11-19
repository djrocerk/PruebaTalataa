import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Detalleorder } from 'src/app/core/models/detalleOrder.interface';
import { environment } from 'src/environment/enviroment';
import { LoginService } from '../../login/service/login.service';

@Injectable({
  providedIn: 'root'
})
export class DetalleOrdenService {

  constructor(private authService: LoginService,private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getuserToken();
    return new HttpHeaders({
      'Authorization': token ? `Bearer ${token}` : '',
      'Content-Type': 'application/json'
    });
  }

  getDetalleOrden(id: number): Observable<Detalleorder[]> {
    const url = `${environment.api}/detalle/${id}`;
    const headers = this.getAuthHeaders();
    return this.http.get<Detalleorder[]>(url, { headers });
  }
}
