import { Component, OnInit } from '@angular/core';
import { ProductsService } from '../../service/products.service';
import { Products } from 'src/app/core/models/products.interface';

@Component({
  selector: 'app-products-page',
  templateUrl: './products-page.component.html',
  styleUrls: ['./products-page.component.scss'],
})
export class ProductsPageComponent implements OnInit {
  dataProducts: Products[] = [];
  sidebarOpen: boolean = false;
  searchQuery: string = '';
  selectedCategory: string = '';
  selectedPriceRange: string = '';

  categories: string[] = ['Electrónicos', 'Alimentos', 'Deportivos'];
  priceRanges: string[] = ['$1 - $5,000', '$5,001 - $10,000', '$10,001 - $20,000'];

  constructor(private productsService: ProductsService) {}

  ngOnInit(): void {
    this.listProducts();
  }

  listProducts() {
    this.productsService.getProducts().subscribe({
      next: (data) => {
        this.dataProducts = data;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  toggleSidebarVisibility() {
    this.sidebarOpen = !this.sidebarOpen;
  }

  searchProducts() {
    if (this.searchQuery.trim()) {
      this.selectedCategory = '';
      this.selectedPriceRange = '';
      
      this.productsService.searchProducts(this.searchQuery).subscribe({
        next: (data) => {
          this.dataProducts = data;
        },
        error: (err) => {
          console.log(err);
        },
      });
    } else {
      this.listProducts();
    }
  }

  filterByCategory() {
    if (this.selectedCategory) {
      this.searchQuery = '';
      this.selectedPriceRange = '';
      
      this.productsService.filterByCategory(this.selectedCategory).subscribe({
        next: (data) => {
          this.dataProducts = data;
        },
        error: (err) => {
          console.log(err);
        },
      });
    } else {
      this.listProducts();
    }
  }

  filterByPriceRange() {
    if (this.selectedPriceRange) {
      this.searchQuery = '';
      this.selectedCategory = '';
      
      const [min, max] = this.selectedPriceRange.split('-').map(val => parseFloat(val.replace(/[^0-9]/g, '')));
      this.productsService.filterByPriceRange(min, max).subscribe({
        next: (data) => {
          this.dataProducts = data;
        },
        error: (err) => {
          console.log(err);
        },
      });
    } else {
      this.listProducts();
    }
  }
}
