import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { LoginService } from '../modules/login/service/login.service';

@Injectable({
  providedIn: 'root'
})
export class onTokenGuard implements CanActivate {

  constructor(private authService: LoginService, private router: Router) {}

  canActivate(): boolean {
    const token = this.authService.getuserToken();

    if (token) {
      this.router.navigate(['/home/products']);
      return false;
    } else {
      return true;
    }
  }
}
