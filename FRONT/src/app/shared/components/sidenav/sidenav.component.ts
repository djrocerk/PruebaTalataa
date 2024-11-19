import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/modules/login/service/login.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
})
export class SidenavComponent {
  @ViewChild('sidenav') sidenav!: MatSidenav;

  constructor(private loginService: LoginService, private router: Router) { }

  ngAfterViewInit() {
  }

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
  
}
