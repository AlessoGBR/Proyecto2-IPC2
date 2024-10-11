import { Component, OnInit , Output, EventEmitter} from '@angular/core';
import { EtiquetaService } from 'app/Servicios/ServicioRegistro/etiqueta.service';
import { Etiqueta } from 'app/Objetos/Etiqueta';
import { EtiquetaComponent } from '../../etiqueta/etiqueta/etiqueta.component';

@Component({
  selector: 'app-seleccion-etiquetas',
  standalone: true,
  imports: [EtiquetaComponent],
  templateUrl: './seleccion-etiquetas.component.html',
  styleUrl: './seleccion-etiquetas.component.css'
})
export class SeleccionEtiquetasComponent implements OnInit {
  etiquetasExistentes: Etiqueta[] = [];
  etiquetasSeleccionadas: string[] = [];
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

  agregarEtiqueta(etiqueta: string): void {
    if (!this.etiquetasSeleccionadas.includes(etiqueta)) {
      this.etiquetasSeleccionadas.push(etiqueta);
    }
  }

  quitarEtiqueta(etiqueta: string): void {
    this.etiquetasSeleccionadas = this.etiquetasSeleccionadas.filter(e => e !== etiqueta);
  }
}
