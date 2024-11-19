import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DetalleOrdenComponent } from './pages/detalle-orden/detalle-orden.component';

const routes: Routes = [
  {
    path: '',
    component: DetalleOrdenComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DetalleOrdenRoutingModule { }
