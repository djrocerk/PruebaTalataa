import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrdenRoutingModule } from './orden-routing.module';
import { OrdenPageComponent } from './pages/orden-page/orden-page.component';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    OrdenPageComponent
  ],
  imports: [
    CommonModule,
    OrdenRoutingModule,
    SharedModule
  ]
})
export class OrdenModule { }
