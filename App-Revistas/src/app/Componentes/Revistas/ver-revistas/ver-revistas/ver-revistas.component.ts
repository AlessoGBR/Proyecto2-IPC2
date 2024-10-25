import { Component, Input, OnInit } from '@angular/core';
import { TarjetaRevistaComponent } from '../../tarjeta-revista/tarjeta-revista/tarjeta-revista.component';
import { CommonModule } from '@angular/common';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { Revista } from 'app/Objetos/Revista';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-ver-revistas',
  standalone: true,
  imports: [TarjetaRevistaComponent, CommonModule],
  templateUrl: './ver-revistas.component.html',
  styleUrl: './ver-revistas.component.css'
})
export class VerRevistasComponent implements OnInit {

  @Input() revistas!: Revista[];
  @Input() fecha!: Date;
  usuario: string| null = null;
  constructor(
    private obtener: ObtenerObjetosService,
    
    ) {      
      this.obtener.obtenerRevistasUsuario().subscribe((respuesta: Revista[]) => {
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
}
