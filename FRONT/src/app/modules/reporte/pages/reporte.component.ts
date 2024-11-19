import { Component, OnInit } from '@angular/core';
import { ReporteService } from '../service/reporte.service';

@Component({
  selector: 'app-reporte',
  templateUrl: './reporte.component.html',
  styleUrls: ['./reporte.component.scss'],
})
export class ReporteComponent implements OnInit {
  
  frequentClients: any[] = [];
  topSoldProducts: any[] = [];
  activeProductsCount: number = 0;

  constructor(private reporteService: ReporteService) {}

  ngOnInit(): void {
    this.loadFrequentClients();
    this.loadTopSoldProducts();
  
    this.reporteService.activeProductsCount$.subscribe({
      next: (count) => {
        console.log('Productos activos actualizados:', count);
        this.activeProductsCount = count;
      },
      error: (err) => {
        console.error('Error al actualizar productos activos:', err);
      },
    });
  }
  
  loadFrequentClients(): void {
    this.reporteService.getFrequentClients().subscribe({
      next: (clients) => {
        this.frequentClients = clients;
      },
      error: (err) => {
        console.error('Error al cargar los clientes frecuentes:', err);
      },
    });
  }

  loadTopSoldProducts(): void {
    this.reporteService.getTopSoldProducts().subscribe({
      next: (products) => {
        
        this.topSoldProducts = products.map((product: any[]) => ({
          productoNombre: product[0],
          totalVendido: product[1],
        }));
      },
      error: (err) => {
        console.error('Error al cargar los productos más vendidos:', err);
      },
    });
  }
}
