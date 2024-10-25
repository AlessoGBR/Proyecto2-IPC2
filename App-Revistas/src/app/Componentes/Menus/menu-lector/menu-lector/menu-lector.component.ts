import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { RouterOutlet } from '@angular/router';
import { VerRevistasComponent } from 'app/Componentes/Revistas/ver-revistas/ver-revistas/ver-revistas.component';
import { BuscarComponent } from "../../../Revistas/buscar/buscar/buscar.component";
import { CommonModule } from '@angular/common';
import { VerSuscripcionesComponent } from 'app/Componentes/CompLector/ver-suscripciones/ver-suscripciones/ver-suscripciones.component';

@Component({
  selector: 'app-menu-lector',
  standalone: true,
  imports: [RouterOutlet, VerRevistasComponent, BuscarComponent,CommonModule,VerSuscripcionesComponent],
  templateUrl: './menu-lector.component.html',
  styleUrl: './menu-lector.component.css'
})
export class MenuLectorComponent implements OnInit {

  username : string | null = null;
  revistas: boolean = false;
  suscripcion: boolean = false;

  constructor(private route: ActivatedRoute){

  }

  ngOnInit(): void {
    this.username = sessionStorage.getItem('username');
  }

  toggleRevista() {
    this.revistas = !this.revistas; 
    this.suscripcion = false;
  }
 
  toggleSuscripcion() {
    this.suscripcion = !this.suscripcion; 
    this.revistas = false;
  }
}
