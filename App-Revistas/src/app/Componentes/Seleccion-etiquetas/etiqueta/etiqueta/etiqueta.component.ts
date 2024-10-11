import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Etiqueta } from 'app/Objetos/Etiqueta';
import { EtiquetaService } from 'app/Servicios/ServicioRegistro/etiqueta.service';

@Component({
  selector: 'app-etiqueta',
  standalone: true,
  imports: [],
  templateUrl: './etiqueta.component.html',
  styleUrl: './etiqueta.component.css'
})
export class EtiquetaComponent {
  @Input() etiquetaIn!: { nombre: string };
  @Input() tipoBoton!: boolean; 
  @Output() agregar = new EventEmitter<string>();
  @Output() quitar = new EventEmitter<string>();

  agregarEtiqueta(nombre: string) {
    this.agregar.emit(nombre);
  }

  quitarEtiqueta(nombre: string) {
    this.quitar.emit(nombre);
  }
}
