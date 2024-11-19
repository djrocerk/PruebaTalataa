import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  role: string | null = null;

  constructor() {}

  ngOnInit(): void {
    this.role = sessionStorage.getItem('role');
    console.log('Rol detectado:', this.role);
  }
}
