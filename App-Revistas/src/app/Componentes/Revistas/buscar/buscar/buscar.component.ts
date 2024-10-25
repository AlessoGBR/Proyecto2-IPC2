import { Component, OnInit } from '@angular/core';
import { Etiqueta } from 'app/Objetos/Etiqueta';
import { Revista } from 'app/Objetos/Revista';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { Usuario } from 'app/Objetos/Usuario';
import { SeleccionEtiquetasComponent } from 'app/Componentes/Seleccion-etiquetas/selecion-etiquetas/seleccion-etiquetas/seleccion-etiquetas.component';
import { VerRevistasComponent } from '../../ver-revistas/ver-revistas/ver-revistas.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-buscar',
  standalone: true,
  imports: [SeleccionEtiquetasComponent, VerRevistasComponent],
  templateUrl: './buscar.component.html',
  styleUrl: './buscar.component.css'
})
export class BuscarComponent implements OnInit {

  revistas!: Revista[];
  usuario: Usuario| null = null;
  fecha: Date = new Date();
  etiquetas!: Etiqueta[];

  constructor(
    private obtener: ObtenerObjetosService,
  ) {
  }

  ngOnInit(): void {    

  }

  recibirEtiquetas(etiquetas: Etiqueta[]) {
    this.etiquetas = etiquetas;
  }

  actualizarRevistas() {
    if (this.etiquetas.length === 0) {
      this.ngOnInit();
    } else {
      this.obtener.obtenerRevistasBusqueda(this.etiquetas).subscribe(
        (respuesta: Revista[]) => {
          if (respuesta.length === 0) {
            Swal.fire({
              icon: 'error',
              title: 'No se encontraron revistas',
              text: 'No hay revistas con etiquetas buscadas!',
              confirmButtonText: 'Aceptar'
            });
          } else {
            this.revistas = respuesta;
          }
        },
        (error: any) => {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Ocurrió un problema al buscar las revistas.',
            confirmButtonText: 'Aceptar'
          });
        }
      );
    }
  }
}
