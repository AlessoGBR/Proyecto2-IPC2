import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { RouterOutlet } from '@angular/router';
import { CrearAnuncioComponent } from 'app/Componentes/CompAnunciante/crearAnuncio/crear-anuncio/crear-anuncio.component';
import { CommonModule } from '@angular/common';
import { VerAnunciosComponent } from "../../../CompAnunciante/verAnuncios/ver-anuncios/ver-anuncios.component";

@Component({
  selector: 'app-menu-anunciante',
  standalone: true,
  imports: [RouterOutlet, CrearAnuncioComponent, CommonModule, VerAnunciosComponent],
  templateUrl: './menu-anunciante.component.html',
  styleUrl: './menu-anunciante.component.css'
})
export class MenuAnuncianteComponent {

  username : string | null = null;
  crearAnuncio: boolean = false;
  cartera: boolean = false;
  verAnuncios: boolean = false;

  constructor(private route: ActivatedRoute){

  }

  ngOnInit(): void {
    this.username = sessionStorage.getItem('username');
  }

  togglePublicarAnuncio() {
    this.crearAnuncio = !this.crearAnuncio; 
    this.cartera = false;
    this.verAnuncios = false;
  }

  toggleVerAnuncios() {
    this.verAnuncios = !this.verAnuncios; 
    this.crearAnuncio = false;
    this.cartera = false;
  }


  toggleCartera() {
    this.cartera = !this.cartera; 
    this.crearAnuncio = false;   
    this.verAnuncios = false; 
  }
}
