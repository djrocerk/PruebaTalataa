import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductsRoutingModule } from './products-routing.module';
import { ProductsPageComponent } from './pages/products-page/products-page.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { MaterialModule } from '../material/material.module';
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    ProductsPageComponent
  ],
    imports: [
        CommonModule,
        ProductsRoutingModule,
        SharedModule,
        MaterialModule,
        FormsModule
    ]
})
export class ProductsModule { }
