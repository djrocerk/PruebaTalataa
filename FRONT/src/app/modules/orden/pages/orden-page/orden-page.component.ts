import { Component, OnInit } from '@angular/core';
import { OrdenService } from '../../service/orden.service';
import { Order } from 'src/app/core/models/order.interface';

@Component({
  selector: 'app-orden-page',
  templateUrl: './orden-page.component.html',
  styleUrls: ['./orden-page.component.scss'],
})
export class OrdenPageComponent implements OnInit {
  dataOrder: Order[] = [];

  constructor(private ordenService: OrdenService) {}
  ngOnInit(): void {
    this.listOrder();
  }

  listOrder() {
    this.ordenService.getOrdenes().subscribe({
      next: (data) => {
        console.log(data);
        this.dataOrder = data;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  onOrderDeleted() {
    this.listOrder();
  }

}
