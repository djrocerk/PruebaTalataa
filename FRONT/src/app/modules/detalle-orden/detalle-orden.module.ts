import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DetalleOrdenRoutingModule } from './detalle-orden-routing.module';
import { DetalleOrdenComponent } from './pages/detalle-orden/detalle-orden.component';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    DetalleOrdenComponent
  ],
  imports: [
    CommonModule,
    DetalleOrdenRoutingModule,
    SharedModule
  ]
})
export class DetalleOrdenModule { }
