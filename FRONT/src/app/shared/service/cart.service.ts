import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Products } from 'src/app/core/models/products.interface';
import { LoginService } from 'src/app/modules/login/service/login.service';


@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cartItems: Products[] = [];
  cartUpdated$ = new Subject<void>();
  itemAddedToCart$ = new Subject<void>();
  private descuentosActivos: boolean = false;
  private descuentoTemporal: boolean = false;

  constructor(private http: HttpClient, private authService: LoginService) {
    this.loadCartItems();
    this.checkDescuentosActivos();
  }

  enableTemporaryDiscount() {
    this.descuentoTemporal = true;
    this.cartUpdated$.next();

    setTimeout(() => {
      this.descuentoTemporal = false;
      this.cartUpdated$.next();
    }, 60000);
  }

  isTemporaryDiscountActive(): boolean {
    return this.descuentoTemporal;
  }

  addToCart(product: Products) {
    const exists = this.cartItems.some((item) => item.id === product.id);
    if (!exists) {
      this.cartItems.push(product);
      this.saveCartItems();
      this.cartUpdated$.next();
      this.itemAddedToCart$.next();
    }
  }

  getCartItems() {
    return this.cartItems;
  }

  removeFromCart(product: Products) {
    const index = this.cartItems.findIndex((item) => item === product);
    if (index !== -1) {
      this.cartItems.splice(index, 1);
      this.saveCartItems();
    }
  }

  updateProductQuantity(productId: number, quantity: number) {
    const product = this.cartItems.find((item) => item.id === productId);
    if (product) {
      product.cantidad = quantity;
      this.saveCartItems();
    }
  }

  getDescuentosActivos(): boolean {
    return this.descuentosActivos;
  }

  private checkDescuentosActivos() {
    const url = 'http://localhost:8083/api/activos';
    const headers = this.getAuthHeaders();

    this.http.get<boolean>(url, { headers }).subscribe({
      next: (response: any) => {
        this.descuentosActivos = response.length > 0;
      },
      error: (err) => {
        console.error('Error al verificar descuentos:', err);
      },
    });
  }

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getuserToken();
    return new HttpHeaders({
      'Authorization': token ? `Bearer ${token}` : '',
      'Content-Type': 'application/json',
    });
  }

  private saveCartItems() {
    localStorage.setItem('cartItems', JSON.stringify(this.cartItems));
  }

  private loadCartItems() {
    const storedItems = localStorage.getItem('cartItems');
    if (storedItems) {
      this.cartItems = JSON.parse(storedItems);
    }
  }

  clearCart() {
    this.cartItems = [];
    this.saveCartItems();
  }

  getAllProducts(): Observable<Products[]> {
    const url = 'http://localhost:8083/api/producto';
    const headers = this.getAuthHeaders();
  
    return this.http.get<Products[]>(url, { headers });
  }
  
  
}
