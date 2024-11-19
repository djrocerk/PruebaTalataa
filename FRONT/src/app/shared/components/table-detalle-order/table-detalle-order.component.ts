import { Component, Input } from '@angular/core';
import { Detalleorder } from 'src/app/core/models/detalleOrder.interface';

@Component({
  selector: 'app-table-detalle-order',
  templateUrl: './table-detalle-order.component.html',
  styleUrls: ['./table-detalle-order.component.scss']
})
export class TableDetalleOrderComponent {

  @Input() detalleOrders: Detalleorder[] = [];
}
