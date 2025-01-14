import { Component, Input, Output, OnInit } from '@angular/core';
import { Etiqueta } from 'app/Objetos/Etiqueta';
import { EtiquetaComponent } from '../etiqueta/etiqueta/etiqueta.component';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-editar-etiquetas',
  standalone: true,
  imports: [EtiquetaComponent, CommonModule],
  templateUrl: './editar-etiquetas.component.html',
  styleUrl: './editar-etiquetas.component.css'
})
export class EditarEtiquetasComponent implements OnInit {

  etiquetasExistentes!: Etiqueta[];
  @Input() etiquetasSeleccionadas!: Etiqueta[];
  editar: boolean = true;
  username: string | null = null;

  constructor(
    private ObtenerObjetosService: ObtenerObjetosService,
    private ruta: ActivatedRoute,
  ) {
    let username = this.ruta.snapshot.paramMap.get('usuario');
    if (username != null) {      
      this.username = username;
    } else {
      this.username = sessionStorage.getItem('username');      
    }
    
  }

  ngOnInit(): void {
    this.etiquetasSeleccionadas = [];
    this.etiquetasExistentes = [];
    this.ObtenerObjetosService.obtenerEtiquetas(this.username!).subscribe((nuevasEtiquetas: Etiqueta[]) => {
      if (nuevasEtiquetas != null) {
        this.etiquetasExistentes = nuevasEtiquetas;
        //this.quitarSeleccionadas();
      }
    });
    }
  agregarEtiqueta(etiqueta: string) {
    if (this.editar) {
      for (let i = 0; i < this.etiquetasExistentes.length; i++) {
        const element = this.etiquetasExistentes[i];
        if (element.nombre == etiqueta) {
          this.etiquetasExistentes.splice(i, 1);
          //this.etiquetasSeleccionadas.push(new Etiqueta(etiqueta));
          break;
        }
      }
    }

  }

  quitarEtiqueta(etiqueta: string) {
    if (this.editar) {
      for (let i = 0; i < this.etiquetasSeleccionadas.length; i++) {
        const element = this.etiquetasSeleccionadas[i];
        if (element.nombre == etiqueta) {
          this.etiquetasSeleccionadas.splice(i, 1);
          //this.etiquetasExistentes.push(new Etiqueta(etiqueta));
          break;
        }
      }
    }
  }
}


