import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cerrar-sesion',
  standalone: true,
  imports: [],
  templateUrl: './cerrar-sesion.component.html',
  styleUrl: './cerrar-sesion.component.css',
})
export class CerrarSesionComponent implements OnInit {
  constructor(public redireccionar: Router) {}

  ngOnInit(): void {}

  cerrarSesion() {
    sessionStorage.clear;
    this.enviarPagina('/Inicio');
  }

  enviarPagina(direccion: string) {
    this.redireccionar.navigate([direccion]);
  }
}
