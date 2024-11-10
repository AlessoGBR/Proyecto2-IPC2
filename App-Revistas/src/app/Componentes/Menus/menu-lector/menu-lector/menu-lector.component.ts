import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { RouterOutlet } from '@angular/router';
import { VerRevistasComponent } from 'app/Componentes/Revistas/ver-revistas/ver-revistas/ver-revistas.component';
import { BuscarComponent } from '../../../Revistas/buscar/buscar/buscar.component';
import { CommonModule } from '@angular/common';
import { VerSuscripcionesComponent } from 'app/Componentes/CompLector/ver-suscripciones/ver-suscripciones/ver-suscripciones.component';
import { VerPerfilComponent } from 'app/Componentes/CompLector/ver-perfil/ver-perfil/ver-perfil.component';
import { PrevisualizarComponent } from "../../../Revistas/previsualizar/previsualizar/previsualizar.component";
import { AnuncioComponent } from "../../../anuncio/anuncio/anuncio.component";
import { CrearAnuncioComponent } from "../../../CompAnunciante/crearAnuncio/crear-anuncio/crear-anuncio.component";
import { CerrarSesionComponent } from "../../cerrar-session/cerrar-sesion/cerrar-sesion.component";

@Component({
  selector: 'app-menu-lector',
  standalone: true,
  imports: [
    RouterOutlet,
    VerRevistasComponent,
    BuscarComponent,
    CommonModule,
    VerSuscripcionesComponent,
    VerPerfilComponent,
    PrevisualizarComponent,
    AnuncioComponent,
    CrearAnuncioComponent,
    CerrarSesionComponent
],
  templateUrl: './menu-lector.component.html',
  styleUrl: './menu-lector.component.css',
})
export class MenuLectorComponent implements OnInit {
  username: string | null = null;
  revistas: boolean = false;
  suscripcion: boolean = false;
  perfil: boolean = false;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.username = sessionStorage.getItem('username');
  }

  toggleRevista() {
    this.revistas = !this.revistas;
    this.suscripcion = false;
    this.perfil = false;
  }

  toggleSuscripcion() {
    this.suscripcion = !this.suscripcion;
    this.revistas = false;
    this.perfil = false;
  }

  tooglePerfil() {
    this.perfil = !this.perfil;
    this.revistas = false;
    this.suscripcion = false;
  }
}
