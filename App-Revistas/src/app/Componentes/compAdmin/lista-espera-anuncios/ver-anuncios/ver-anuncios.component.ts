import { Component, OnInit } from '@angular/core';
import { Anuncio } from 'app/Objetos/Anuncio';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';

@Component({
  selector: 'app-ver-anuncios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ver-anuncios.component.html',
  styleUrl: './ver-anuncios.component.css',
})
export class VerAnunciosComponent implements OnInit {
  espera: boolean = false;
  anuncios!: Anuncio[];
  username!: string;

  constructor(private obtener: ObtenerObjetosService,
    private registro: RegistroService
  ) {
    this.username = sessionStorage.getItem('username')!;
    this.obtener.ObtenerAnuncios().subscribe(
      (respuesta: Anuncio[]) => {
        this.anuncios = respuesta;
      },
      (error: any) => {
        this.espera = true;
      }
    );
  }

  ngOnInit(): void {}

  denegarAnuncio(idAnuncio?: number) {
    if (idAnuncio === undefined) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'No se pudo aprobar la revista, intenta nuevamente',
        confirmButtonText: 'Aceptar',
      });
      return;
    }
    this.registro.denegarAnuncio(idAnuncio).subscribe({
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
          text: 'No se pudo denegar la revista, intenta nuevamente',
          confirmButtonText: 'Aceptar',
        });
      },
    });
  }

  aprobarAnuncio(idAnuncio?: number) {
    if (idAnuncio === undefined) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'No se pudo aprobar la revista, intenta nuevamente',
        confirmButtonText: 'Aceptar',
      });
      return;
    }
    this.registro.aceptarAnuncio(idAnuncio).subscribe({
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
}
