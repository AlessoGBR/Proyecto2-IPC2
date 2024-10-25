import { Component, OnInit } from '@angular/core';
import { Revista } from 'app/Objetos/Revista';
import { CommonModule } from '@angular/common';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';

@Component({
  selector: 'app-lista-aprobadas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-aprobadas.component.html',
  styleUrl: './lista-aprobadas.component.css',
})
export class ListaAprobadasComponent implements OnInit {
  
  espera: boolean = true;
  revistas!: Revista[];

  ngOnInit(): void {
    
  }

  constructor(
    private obtener: ObtenerObjetosService,
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
}
