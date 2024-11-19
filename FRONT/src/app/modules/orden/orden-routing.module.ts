import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrdenPageComponent } from './pages/orden-page/orden-page.component';

const routes: Routes = [
  {
    path: '',
    component: OrdenPageComponent
  },
  {
    path: 'detalle/:id',
    loadChildren: () => import('../detalle-orden/detalle-orden.module').then(m => m.DetalleOrdenModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrdenRoutingModule { }
