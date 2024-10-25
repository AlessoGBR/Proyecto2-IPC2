import { Component, OnInit } from '@angular/core';
import { Revista } from 'app/Objetos/Revista';
import { AlmacenamientoService } from 'app/Servicios/Revistas/almacenamiento-service/almacenamiento-service.service';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-publicaciones',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './publicaciones.component.html',
  styleUrl: './publicaciones.component.css'
})
export class PublicacionesComponent implements OnInit{


  revistas!: Revista[];
  usuario: string| null = null;
  

  constructor(
    private obtener: ObtenerObjetosService,
    private almacenamiento: AlmacenamientoService,
    private registrar: RegistroService
  ) {
    this.usuario = sessionStorage.getItem('username');
    this.obtener.obtenerRevistasPorEditor(this.usuario).subscribe((respuesta: Revista[]) => {
      if (respuesta != null) {
        this.revistas = respuesta;
      }
    },
      (error: any) => {
        Swal.fire({
          icon: 'error',
          title: 'No se encontraron revistas',
          text: 'Ocurrió un error, por favor intenta nuevamente.',
          confirmButtonText: 'Aceptar'
        });
      }
    );
  }

  ngOnInit(): void {
  }

  cambioComentarios(event: Event, idRevista?: number) {
    const inputElement = event.target as HTMLInputElement;
    const comentariosActivos = inputElement.checked; 
  
    if (idRevista !== undefined) {
      this.registrar.cambioComentario(idRevista, comentariosActivos).subscribe(
        (respuesta: any) => {
          Swal.fire({
            icon: 'success',
            title: 'Cambio realizado',
            text: 'Revista editada.',
            confirmButtonText: 'Aceptar'
          });
        },
        (error: any) => {
          Swal.fire({
            icon: 'error',
            title: 'No se pudo editar la revista',
            text: 'Ocurrió un error, por favor intenta nuevamente.',
            confirmButtonText: 'Aceptar'
          });
        }
      );
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Error al actualizar',
        text: 'El ID de la revista no es válido.',
        confirmButtonText: 'Aceptar'
      });
    }
  }

  cambioMeGusta(event: Event, idRevista?: number) {
    const inputElement = event.target as HTMLInputElement;
    const comentariosActivos = inputElement.checked; 
  
    if (idRevista !== undefined) {
      this.registrar.cambioMeGusta(idRevista, comentariosActivos).subscribe(
        (respuesta: any) => {
          Swal.fire({
            icon: 'success',
            title: 'Cambio realizado',
            text: 'Revista editada.',
            confirmButtonText: 'Aceptar'
          });
        },
        (error: any) => {
          Swal.fire({
            icon: 'error',
            title: 'No se pudo editar la revista',
            text: 'Ocurrió un error, por favor intenta nuevamente.',
            confirmButtonText: 'Aceptar'
          });
        }
      );
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Error al actualizar',
        text: 'El ID de la revista no es válido.',
        confirmButtonText: 'Aceptar'
      });
    }
  }

  cambioSuscripciones(event: Event, idRevista?: number) {
    const inputElement = event.target as HTMLInputElement;
    const comentariosActivos = inputElement.checked; 
  
    if (idRevista !== undefined) {
      this.registrar.cambioSuscripciones(idRevista, comentariosActivos).subscribe(
        (respuesta: any) => {
          Swal.fire({
            icon: 'success',
            title: 'Cambio realizado',
            text: 'Revista editada.',
            confirmButtonText: 'Aceptar'
          });
        },
        (error: any) => {
          Swal.fire({
            icon: 'error',
            title: 'No se pudo editar la revista',
            text: 'Ocurrió un error, por favor intenta nuevamente.',
            confirmButtonText: 'Aceptar'
          });
        }
      );
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Error al actualizar',
        text: 'El ID de la revista no es válido.',
        confirmButtonText: 'Aceptar'
      });
    }
  }

  editarRevista(idRevista?: number) {
    
  }


}
