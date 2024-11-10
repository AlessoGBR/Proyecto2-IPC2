import { Component, OnInit } from '@angular/core';
import { Revista } from 'app/Objetos/Revista';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lista-denegadas',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './lista-denegadas.component.html',
  styleUrl: './lista-denegadas.component.css',
})
export class ListaDenegadasComponent implements OnInit {
  espera: boolean = true;
  revistas!: Revista[];

  ngOnInit(): void {}

  constructor(private obtener: ObtenerObjetosService) {
    this.obtener.obtenerRevistasDenegadas().subscribe(
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
