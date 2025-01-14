import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ListaEsperaComponent } from 'app/Componentes/compAdmin/lista-espera/lista-espera/lista-espera.component';
import { ListaDenegadasComponent } from 'app/Componentes/compAdmin/lista-denegadas/lista-denegadas/lista-denegadas.component';
import { ListaAprobadasComponent } from 'app/Componentes/compAdmin/lista-aprobadas/lista-aprobadas/lista-aprobadas.component';
import { CommonModule } from '@angular/common';
import { VerAnunciosComponent } from 'app/Componentes/compAdmin/lista-espera-anuncios/ver-anuncios/ver-anuncios.component';
import { AnunciosAprobadosComponent } from "../../../compAdmin/lista-aprobadas-anuncio/anuncios-aprobados/anuncios-aprobados.component";
import { ReporteAdminComponent } from "../../../Reportes/ReporteAdmin/reporte-admin/reporte-admin.component";
import { CerrarSesionComponent } from "../../cerrar-session/cerrar-sesion/cerrar-sesion.component";
import { PrecioAnuncioComponent } from 'app/Componentes/compAdmin/precio-anuncio/precio-anuncio/precio-anuncio.component';
import { PrecioRevistaComponent } from "../../../compAdmin/precio-revista/precio-revista/precio-revista.component";

@Component({
  selector: 'app-menu-admin',
  standalone: true,
  imports: [ListaEsperaComponent, RouterOutlet, CommonModule, ListaAprobadasComponent, PrecioAnuncioComponent, VerAnunciosComponent, AnunciosAprobadosComponent, ReporteAdminComponent, CerrarSesionComponent, PrecioRevistaComponent],
  templateUrl: './menu-admin.component.html',
  styleUrl: './menu-admin.component.css'
})
export class MenuAdminComponent {

  revistas: boolean = true;
  anuncios: boolean = false;
  reportes: boolean = false;
  espera: boolean = true;
  precioAnuncio: boolean = false;
  precioRevista: boolean = false;
  rechazados: boolean = false;

  Revistas(){
    this.revistas = !this.revistas;
    this.anuncios = false;
    this.reportes = false;
    this.precioAnuncio = false;
    this.precioRevista = false;
  }

  Anuncios(){
    this.anuncios = !this.anuncios;
    this.revistas =false;
    this.reportes = false;
    this.precioAnuncio = false;
    this.precioRevista = false;
  }

  Reportes(){
    this.reportes = !this.reportes;
    this.anuncios = false;
    this.revistas = false;
    this.precioAnuncio = false;
    this.precioRevista = false;
  }

  PrecioAnuncio(){
    this.precioAnuncio = !this.precioAnuncio;
    this.anuncios = false;
    this.revistas = false;
    this.reportes = false;
    this.precioRevista = false;
  }

  PrecioRevista(){
    this.precioRevista = !this.precioRevista;
    this.anuncios = false;
    this.revistas = false;
    this.reportes = false;
    this.precioAnuncio = false;
  }
}
