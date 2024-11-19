import { Component, ViewChild, TemplateRef } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/modules/login/service/login.service';
import { Descuento, DescuentoService } from '../../service/descuento.service';


@Component({
  selector: 'app-sidenav-admin',
  templateUrl: './sidenav-admin.component.html',
  styleUrls: ['./sidenav-admin.component.scss']
})
export class SidenavAdminComponent {
  @ViewChild('sidenav') sidenav!: MatSidenav;
  @ViewChild('modalTemplate') modalTemplate!: TemplateRef<any>;

  fechaActual: Date = new Date();
  fechaFinal!: Date;

  constructor(
    private loginService: LoginService,
    private router: Router,
    private dialog: MatDialog,
    private descuentoService: DescuentoService
  ) {}

  ngAfterViewInit() {}

  toggleSidenav() {
    if (this.sidenav) {
      if (this.sidenav.opened) {
        this.sidenav.close();
      } else {
        this.sidenav.open();
      }
    }
  }

  logout() {
    this.loginService.logout();
    window.location.reload();
  }

  openModal() {
    this.dialog.open(this.modalTemplate, {
      width: '400px',
    });
  }

  enviarFechas() {
    if (this.fechaFinal) {
      const nuevoDescuento: Omit<Descuento, 'estado'> = {
        fechaInicio: this.fechaActual,
        fechaFin: this.fechaFinal,
      };

      this.descuentoService.guardarDescuento(nuevoDescuento).subscribe({
        next: (data) => {
          console.log('Descuento guardado:', data);
          alert('Descuento guardado con éxito.');
        },
        error: (err) => {
          console.error('Error al guardar el descuento:', err);
          alert('Error al guardar el descuento.');
        },
      });
    } else {
      alert('Por favor selecciona una fecha final.');
    }
  }
}
