import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './modules/home/pages/home-page/home-page.component';
import { LoginComponent } from './modules/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { onTokenGuard } from './guards/on-token.guard';

const routes: Routes = [
  {
    path: 'login',
    canActivate: [onTokenGuard],
    component: LoginComponent  
  },
  {
    path: 'home',
    component: HomePageComponent,
    canActivate: [AuthGuard],
    loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule)
  },
  {
    path: '**',
    redirectTo: 'home',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
