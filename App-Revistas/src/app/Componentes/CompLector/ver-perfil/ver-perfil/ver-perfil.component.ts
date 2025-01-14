import { Component, OnInit } from '@angular/core';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { Usuario } from 'app/Objetos/Usuario';
import { VerImagenComponent } from 'app/Componentes/inicio/ver-imagen/ver-imagen/ver-imagen.component';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { EditarEtiquetasComponent } from "../../../Seleccion-etiquetas/editar-etiquetas/editar-etiquetas.component";

@Component({
  selector: 'app-ver-perfil',
  standalone: true,
  imports: [CommonModule, VerImagenComponent, EditarEtiquetasComponent],
  templateUrl: './ver-perfil.component.html',
  styleUrl: './ver-perfil.component.css'
})
export class VerPerfilComponent implements OnInit{

  pathFoto!: string;
  username: string | null = null;
  usuario: Usuario | null = null;
  puedeEditar: boolean = false;

  constructor(
    private obtener: ObtenerObjetosService,
    private sanitizer: DomSanitizer,
    private ruta: ActivatedRoute,
  ) {
    let username = this.ruta.snapshot.paramMap.get('usuario');
    if (username != null) {      
      this.username = username;
    } else {
      this.username = sessionStorage.getItem('username');      
    }

  }

  ngOnInit(): void {
    if (this.username) {
      this.obtener.obtenerPerfilUsuario(this.username).subscribe({
        next: (respuesta) => {
          this.usuario = respuesta;
          this.pathFoto = this.usuario.foto;
          if (this.usuario && this.usuario.descripcion) {
            
          } else {
            console.warn('El perfil del usuario no contiene descripción.');
          }
        },
        error: (error) => {
          console.error('Error al obtener el perfil del usuario:', error);
        }
      });
    } else {
      console.warn('No se encontró un nombre de usuario en la sesión.');
    }
  }
  
}
