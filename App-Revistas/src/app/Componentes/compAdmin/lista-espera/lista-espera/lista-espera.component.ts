import { Component, OnInit } from '@angular/core';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { Revista } from 'app/Objetos/Revista';
import { CommonModule } from '@angular/common';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';
import { RouterLink } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-lista-espera',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './lista-espera.component.html',
  styleUrl: './lista-espera.component.css',
})
export class ListaEsperaComponent implements OnInit {
  espera: boolean = true;
  revistas!: Revista[];

  constructor(
    private obtener: ObtenerObjetosService,
    private registro: RegistroService
  ) {
    this.obtener.obtenerRevistasPendientes().subscribe(
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

  ngOnInit(): void {}

  
}
