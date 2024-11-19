import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DetalleOrdenService } from '../../service/detalle-orden.service';
import { Detalleorder } from 'src/app/core/models/detalleOrder.interface';

@Component({
  selector: 'app-detalle-orden',
  templateUrl: './detalle-orden.component.html',
  styleUrls: ['./detalle-orden.component.scss'],
})
export class DetalleOrdenComponent implements OnInit {
  idPedido!: number;
  dataDetalleOrder: Detalleorder[] = [];
  constructor(private route: ActivatedRoute , private detalleService: DetalleOrdenService) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.idPedido = params['id'];
    });

    this.listDetalleOrder();
  }

  listDetalleOrder(){
    this.detalleService.getDetalleOrden(this.idPedido).subscribe({
      next: (data) => {
        this.dataDetalleOrder = data;
      },
      error: (err) => {
        console.log(err);
      },
    })
  }
}
