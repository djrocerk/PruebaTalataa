import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImgBrokenDirective } from './directives/img-broken.directive';
import { MaterialModule } from '../modules/material/material.module';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { CardProductsComponent } from './components/card-products/card-products.component';
import { TableOrderComponent } from './components/table-order/table-order.component';
import {RouterModule} from '@angular/router';
import { TableDetalleOrderComponent } from './components/table-detalle-order/table-detalle-order.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmDeleteDialogComponent } from './components/confirm-delete-dialog/confirm-delete-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { SidenavAdminComponent } from './components/sidenav-admin/sidenav-admin.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
@NgModule({
  declarations: [
    ImgBrokenDirective,
    SidenavComponent,
    CardProductsComponent,
    TableOrderComponent,
    TableDetalleOrderComponent,
    SidebarComponent,
    ConfirmDeleteDialogComponent,
    SidenavAdminComponent,

  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatDatepickerModule,
    MatInputModule,
    MatNativeDateModule,
  ],
  exports: [
    SidenavComponent,
    CardProductsComponent,
    TableOrderComponent,
    TableDetalleOrderComponent,
    SidebarComponent,
    SidenavAdminComponent
  ]
})
export class SharedModule { }
