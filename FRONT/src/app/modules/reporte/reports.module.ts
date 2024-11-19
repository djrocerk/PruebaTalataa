import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReporteComponent } from './pages/reporte.component';
import { MaterialModule } from '../material/material.module';
import {FormsModule} from "@angular/forms";
import { ReportsRoutingModule } from './reports-routing.module';


@NgModule({
  declarations: [
    ReporteComponent
  ],
    imports: [
        CommonModule,
        ReportsRoutingModule,
        MaterialModule,
        FormsModule
    ]
})
export class ReportsModule { }
