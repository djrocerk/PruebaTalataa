import { Component, Input, OnInit } from '@angular/core';
import { CartService } from '../../service/cart.service';
import { Products } from 'src/app/core/models/products.interface';
import { OrdenService } from 'src/app/modules/orden/service/orden.service';
import { Subscription } from 'rxjs';
import { LoginService } from 'src/app/modules/login/service/login.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  @Input() isOpen: boolean = false;
  cartItems: Products[] = [];
  total: number = 0;
  private cartSubscription: Subscription = new Subscription();
  errorCompra: string | null = null;

  constructor(
    private cartService: CartService,
    private ordenService: OrdenService,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    this.getCartItems();
    this.cartSubscription = this.cartService.cartUpdated$.subscribe(() => {
      this.getCartItems();
      this.calculateTotal();
    });
  }

  ngOnDestroy(): void {
    this.cartSubscription.unsubscribe();
  }

  getCartItems() {
    this.cartItems = this.cartService.getCartItems();
    this.calculateTotal();
  }

  removeItemFromCart(item: Products) {
    this.cartService.removeFromCart(item);
    this.getCartItems();
  }

  onQuantityChange(productId: number, event: any) {
    const quantity = parseInt(event.target.value, 10);
    if (!isNaN(quantity) && quantity > 0) {
      this.cartService.updateProductQuantity(productId, quantity);
      this.calculateTotal();
    }
  }

  calculateTotal() {
    const descuentosActivos = this.cartService.getDescuentosActivos();
    const descuentoTemporal = this.cartService.isTemporaryDiscountActive();
    const frecuenciaUsuario = this.loginService.getFrecuencia() || 0;
  
    this.total = this.cartItems.reduce((acc, item) => {
      let itemTotal = item.precio * item.cantidad!;
      if (descuentoTemporal) {
        itemTotal *= 0.5;
      } else if (descuentosActivos) {
        itemTotal *= 0.9;
      }
      console.log(frecuenciaUsuario);
      
      if (frecuenciaUsuario >= 5) {
        itemTotal *= 0.95;
      }
  
      return acc + itemTotal;
    }, 0);
  }  

  comprar() {
    const orderItems = this.cartItems.map((item) => ({
      producto: { id: item.id },
      cantidad: item.cantidad,
    }));

    console.log(orderItems);

    this.ordenService.postOrden(orderItems).subscribe({
      next: (response) => {
        this.cartService.clearCart();
        this.getCartItems();
        this.calculateTotal();
        const nuevaFrecuencia = response.frecuencia;
        if (nuevaFrecuencia !== undefined) {
          this.loginService.updateFrecuencia(nuevaFrecuencia);
        }

        window.location.reload();
      },
      error: (err) => {
        this.errorCompra = err.error;
        console.log('Error al realizar la compra:', err);
      },
    });
  }

  generateRandomCart() {
    this.cartService.getAllProducts().subscribe({
      next: (allProducts) => {
        if (allProducts.length === 0) {
          console.warn('No hay productos disponibles para generar un carrito.');
          return;
        }

        const count = Math.min(3, allProducts.length);
        const randomProducts = this.getRandomProducts(allProducts, count);

        randomProducts.forEach((product) => {
          this.cartService.addToCart({ ...product, cantidad: 1 });
        });

        
        this.cartService.enableTemporaryDiscount();

        this.getCartItems();
      },
      error: (err) => {
        console.error('Error al generar el carrito aleatorio:', err);
      },
    });
  }

  
  private getRandomProducts(products: Products[], count: number): Products[] {
    const shuffled = [...products].sort(() => 0.5 - Math.random());
    return shuffled.slice(0, count);
  }
}
