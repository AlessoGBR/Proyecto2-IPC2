import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { PublicarRevistaComponent } from 'app/Componentes/Revistas/publicar-revista/publicar-revista/publicar-revista.component';
import { AlmacenamientoService } from 'app/Servicios/Revistas/almacenamiento-service/almacenamiento-service.service';
import { CommonModule } from '@angular/common';
import { PublicacionesComponent } from "../../../CompEditor/publicaciones/publicaciones.component";
import { CarteraEditorComponent } from "../../../CompEditor/cartera/cartera-editor/cartera-editor.component";
import { ReporteEditorComponent } from "../../../Reportes/ReporteEditor/reporte-editor/reporte-editor.component";
import { CerrarSesionComponent } from "../../cerrar-session/cerrar-sesion/cerrar-sesion.component";

@Component({
  selector: 'app-menu-editor',
  standalone: true,
  imports: [RouterOutlet, PublicarRevistaComponent, CommonModule, RouterLink, PublicacionesComponent, CarteraEditorComponent, ReporteEditorComponent, CerrarSesionComponent],
  templateUrl: './menu-editor.component.html',
  styleUrl: './menu-editor.component.css'
})
export class MenuEditorComponent {

  mostrarRevistas: boolean = false; 
  mostrarPublicarRevista: boolean = false; 
  cartera: boolean =false;
  reporte: boolean = false;

  togglePublicarRevista() {
    this.mostrarPublicarRevista = !this.mostrarPublicarRevista; 
    this.mostrarRevistas = false;
    this.cartera =false;
    this.reporte = false;
  }

  toggleRevistasCreadas() {
    this.mostrarPublicarRevista = false;
    this.mostrarRevistas = !this.mostrarRevistas; 
    this.cartera = false;
    this.reporte = false;
  }

  toggleCartera() {
    this.cartera = !this.cartera;
    this.mostrarPublicarRevista = false;
    this.mostrarRevistas = false
    this.reporte = false;
  }

  toggleReporte() {
    this.reporte = !this.reporte;
    this.cartera = false;
    this.mostrarPublicarRevista = false;
    this.mostrarRevistas = false
  }

}
