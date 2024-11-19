import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginService } from 'src/app/modules/login/service/login.service';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ReporteService {
  private baseUrl = 'http://localhost:8083/api';
  private activeProductsCountSubject = new BehaviorSubject<number>(0);
  activeProductsCount$ = this.activeProductsCountSubject.asObservable();

  constructor(private http: HttpClient, private authService: LoginService) {
    this.updateActiveProductsCount();
  }

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getuserToken();
    return new HttpHeaders({
      Authorization: token ? `Bearer ${token}` : '',
      'Content-Type': 'application/json',
    });
  }

  getFrequentClients(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.baseUrl}/top-frecuentes`, { headers });
  }

  getTopSoldProducts(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.baseUrl}/top-vendidos`, { headers });
  }

  updateActiveProductsCount(): void {
    const headers = this.getAuthHeaders();
    this.http
      .get<number>(`${this.baseUrl}/producto/estado/true/count`, { headers })
      .pipe(
        tap((count) => this.activeProductsCountSubject.next(count))
      )
      .subscribe({
        error: (err) => console.error('Error al obtener el total de productos activos:', err),
      });
  }  
}
