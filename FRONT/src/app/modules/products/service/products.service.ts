import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Products } from 'src/app/core/models/products.interface';
import { environment } from 'src/environment/enviroment';
import { LoginService } from '../../login/service/login.service';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private authService: LoginService,private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getuserToken();
    return new HttpHeaders({
      'Authorization': token ? `Bearer ${token}` : '',
      'Content-Type': 'application/json'
    });
  }
  getProducts(): Observable<Products[]> {
    const url = `${environment.api}/producto`
    const headers = this.getAuthHeaders();
    return this.http.get<Products[]>(url, { headers })
  }

  searchProducts(nombreProducto: string): Observable<Products[]> {
    const url = `${environment.api}/producto/${nombreProducto}`;
    const headers = this.getAuthHeaders();
    return this.http.get<Products[]>(url, { headers });
  }
  filterByCategory(category: string): Observable<Products[]> {
    const url = `${environment.api}/producto/categoria/${category}`;
    const headers = this.getAuthHeaders();
    return this.http.get<Products[]>(url, { headers });
  }
  
  filterByPriceRange(min: number, max: number): Observable<Products[]> {
    const url = `${environment.api}/producto/precio?min=${min}&max=${max}`;
    const headers = this.getAuthHeaders();
    return this.http.get<Products[]>(url, { headers });
  }
  
}

