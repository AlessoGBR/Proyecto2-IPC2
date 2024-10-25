import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-etiqueta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './etiqueta.component.html',
  styleUrl: './etiqueta.component.css'
})
export class EtiquetaComponent implements OnInit{
  @Input() etiquetaIn!: { nombre: string };
  @Input() tipoBoton!: boolean; 
  @Output() agregar = new EventEmitter<string>();
  @Output() quitar = new EventEmitter<string>();

  constructor() {
  }

  ngOnInit(): void {
  }

  agregarEtiqueta(dato: string) {
    this.agregar.emit(dato);
  }

  quitarEtiqueta(dato: string) {
    this.quitar.emit(dato);
  }
}
