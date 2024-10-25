import { Component, OnInit } from '@angular/core';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { Revista } from 'app/Objetos/Revista';
import { CommonModule } from '@angular/common';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-lista-espera',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-espera.component.html',
  styleUrl: './lista-espera.component.css',
})
export class ListaEsperaComponent implements OnInit {
  espera: boolean = true;
  revistas!: Revista[];

  constructor(
    private obtener: ObtenerObjetosService,
    private registro: RegistroService
  ) {
    this.obtener.obtenerRevistasPendientes().subscribe(
      (respuesta: Revista[]) => {
        this.revistas = respuesta;
        this.espera = false;
        if (this.revistas.length == 0) {
          this.espera = true;
        }
      },
      (error: any) => {
        this.espera = true;
      }
    );
  }

  ngOnInit(): void {}

  aprobarRevista(idRevista?: number) {
    if (idRevista === undefined) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'No se pudo aprobar la revista, intenta nuevamente',
        confirmButtonText: 'Aceptar',
      });
      return;
    }
    this.registro.aceptarRevista(idRevista).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Aprobacion',
          text: 'Revista aprobada exitosamente',
          confirmButtonText: 'Aceptar',
        }).then(() => {
          location.reload(); 
        });
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudo aprobar la revista, intenta nuevamente',
          confirmButtonText: 'Aceptar',
        });
      },
    });
  }

  denegarRevista(idRevista?: number) {
    if (idRevista === undefined) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'No se pudo aprobar la revista, intenta nuevamente',
        confirmButtonText: 'Aceptar',
      });
      return;
    }
    this.registro.denegarRevista(idRevista).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Denegada',
          text: 'Revista denegada exitosamente',
          confirmButtonText: 'Aceptar',
        }).then(() => {
          location.reload(); 
        });
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudo aprobar la revista, intenta nuevamente',
          confirmButtonText: 'Aceptar',
        });
      },
    });
  }
}
