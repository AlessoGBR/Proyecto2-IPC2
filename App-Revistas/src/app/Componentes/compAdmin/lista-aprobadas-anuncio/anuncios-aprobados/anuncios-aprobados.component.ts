import { Component, OnInit } from '@angular/core';
import { Anuncio } from 'app/Objetos/Anuncio';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';

@Component({
  selector: 'app-anuncios-aprobados',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './anuncios-aprobados.component.html',
  styleUrl: './anuncios-aprobados.component.css',
})
export class AnunciosAprobadosComponent implements OnInit {
  anuncios!: Anuncio[];
  username!: string;
  espera: boolean = false;
  precioActualizado!: number;

  constructor(
    private obtener: ObtenerObjetosService,
    private RegistroService: RegistroService
  ) {
    this.username = sessionStorage.getItem('username')!;
    this.obtener.ObtenerAnunciosAprobados().subscribe(
      (respuesta: Anuncio[]) => {
        this.anuncios = respuesta;
      },
      (error: any) => {
        this.espera = true;
      }
    );
  }
  ngOnInit(): void {}

  modificarPrecio(anuncio: any): void {
      anuncio.pago = this.precioActualizado;
      this.RegistroService.actualizarPrecioAnuncio(anuncio.idAnuncio, anuncio.pago).subscribe({
        next: (response) => {
          Swal.fire({
            icon: 'success',
            title: 'Éxito',
            text: 'El precio de la revista se ha actualizado correctamente.',
            confirmButtonText: 'Aceptar',
          });
        },
        error: (err) => {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo actualizar el precio. Por favor, intenta de nuevo.',
            confirmButtonText: 'Aceptar',
          });
        },
      });
      
    }

  actualizarPrecio(event: Event, revista: any): void {
      const inputElement = event.target as HTMLInputElement; 
      const valor = inputElement.value; 
    
      const precioNumerico = parseFloat(valor);
      if (!isNaN(precioNumerico) && precioNumerico > 0) {
        revista.precio = precioNumerico; 
        this.precioActualizado = precioNumerico; 
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Precio Inválido',
          text: 'Por favor, ingresa un número válido mayor a 0.',
          confirmButtonText: 'Aceptar',
        });
      }
    }
}
