import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { PublicarRevistaComponent } from 'app/Componentes/Revistas/publicar-revista/publicar-revista/publicar-revista.component';
import { AlmacenamientoService } from 'app/Servicios/Revistas/almacenamiento-service/almacenamiento-service.service';
import { CommonModule } from '@angular/common';
import { PublicacionesComponent } from "../../../CompEditor/publicaciones/publicaciones.component";

@Component({
  selector: 'app-menu-editor',
  standalone: true,
  imports: [RouterOutlet, PublicarRevistaComponent, CommonModule, RouterLink, PublicacionesComponent],
  templateUrl: './menu-editor.component.html',
  styleUrl: './menu-editor.component.css'
})
export class MenuEditorComponent {

  mostrarRevistas: boolean = false; 
  mostrarPublicarRevista: boolean = false; 

  togglePublicarRevista() {
    this.mostrarPublicarRevista = !this.mostrarPublicarRevista; 
    this.mostrarRevistas = false;
  }

  toggleRevistasCreadas() {
    this.mostrarPublicarRevista = false;
    this.mostrarRevistas = !this.mostrarRevistas; 
  }

}
