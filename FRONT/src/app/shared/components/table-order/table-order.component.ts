import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { Order } from 'src/app/core/models/order.interface';
import { OrdenService } from 'src/app/modules/orden/service/orden.service';
import {ConfirmDeleteDialogComponent} from "../confirm-delete-dialog/confirm-delete-dialog.component";
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-table-order',
  templateUrl: './table-order.component.html',
  styleUrls: ['./table-order.component.scss'],
})
export class TableOrderComponent {
  @Input() orders: Order[] = [];
  @Output() orderDeleted = new EventEmitter<void>();

  constructor(private dialog: MatDialog, private router: Router, private ordenService: OrdenService) {}

  verMas(idPedido: number) {
    this.router.navigate(['/home/order/detalle', idPedido]);
  }

  deleteOrder(idPedido: number): void {
    const dialogRef = this.dialog.open(ConfirmDeleteDialogComponent);

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.ordenService.deleteOrden(idPedido).subscribe({
          next: () => {
            this.orders = this.orders.filter((order) => order.idPedido !== idPedido);
            this.orderDeleted.emit();
          },
          error: (err) => {
            console.log(err);
          },
        });
      }
    });
  }
}
