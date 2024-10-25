import { Component, OnInit , Output, EventEmitter} from '@angular/core';
import { EtiquetaService } from 'app/Servicios/ServicioRegistro/etiqueta.service';
import { Etiqueta } from 'app/Objetos/Etiqueta';
import { EtiquetaComponent } from '../../etiqueta/etiqueta/etiqueta.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-seleccion-etiquetas',
  standalone: true,
  imports: [EtiquetaComponent,CommonModule],
  templateUrl: './seleccion-etiquetas.component.html',
  styleUrl: './seleccion-etiquetas.component.css'
})

export class SeleccionEtiquetasComponent implements OnInit {
  etiquetasExistentes: Etiqueta[] = [];
  etiquetasSeleccionadas: Etiqueta[] = [];
  
  @Output() enviarEtiquetas = new EventEmitter<Etiqueta[]>();

  constructor(private etiquetaService: EtiquetaService) {}

  ngOnInit(): void {
    this.cargarEtiquetas();
  }

  cargarEtiquetas(): void {
    this.etiquetaService.obtenerEtiquetas().subscribe({
      next: (etiquetas: Etiqueta[]) => {
        this.etiquetasExistentes = etiquetas;
      },
      error: (error) => {
        console.error('Error al obtener las etiquetas', error);
      },
      complete: () => {
        console.log('Finalizó la solicitud de etiquetas');
      }
    });
  }

  agregarEtiqueta(etiqueta: Etiqueta): void {
    const index = this.etiquetasExistentes.findIndex(e => e.nombre === etiqueta.nombre);
    if (index > -1) {
      this.etiquetasExistentes.splice(index, 1);
      this.etiquetasSeleccionadas.push(etiqueta);
      this.enviarEtiquetas.emit(this.etiquetasSeleccionadas);
    }
  }

  quitarEtiqueta(etiqueta: Etiqueta): void {
    const index = this.etiquetasSeleccionadas.findIndex(e => e.nombre === etiqueta.nombre);
    if (index > -1) {
      this.etiquetasSeleccionadas.splice(index, 1);
      this.etiquetasExistentes.push(etiqueta);
      this.enviarEtiquetas.emit(this.etiquetasSeleccionadas);
    }
  }
}
