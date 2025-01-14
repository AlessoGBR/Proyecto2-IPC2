import { Component, OnInit } from '@angular/core';
import { Revista } from 'app/Objetos/Revista';
import { CommonModule } from '@angular/common';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-lista-aprobadas',
  standalone: true,
  imports: [CommonModule,RouterLink, FormsModule],
  templateUrl: './lista-aprobadas.component.html',
  styleUrl: './lista-aprobadas.component.css',
})
export class ListaAprobadasComponent implements OnInit {
  
  espera: boolean = true;
  revistas!: Revista[];
  precioActualizado!: number;

  ngOnInit(): void {
    
  }

  constructor(
    private obtener: ObtenerObjetosService,
    private RegistroService: RegistroService
  ) {
    this.obtener.obtenerRevistasAprobadas().subscribe(
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

  modificarPrecio(revista: any): void {
    revista.precio = this.precioActualizado;  
    this.RegistroService.actualizarPrecioRevista(revista.idRevista, revista.precio).subscribe({
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
    const inputElement = event.target as HTMLInputElement; // Aseguramos que el target es un input
    const valor = inputElement.value; // Obtenemos el valor del input
  
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
