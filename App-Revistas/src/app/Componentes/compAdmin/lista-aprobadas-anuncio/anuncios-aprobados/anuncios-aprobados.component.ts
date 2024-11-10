import { Component, OnInit } from '@angular/core';
import { Anuncio } from 'app/Objetos/Anuncio';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';

@Component({
  selector: 'app-anuncios-aprobados',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './anuncios-aprobados.component.html',
  styleUrl: './anuncios-aprobados.component.css',
})
export class AnunciosAprobadosComponent implements OnInit {
  anuncios!: Anuncio[];
  username!: string;
  espera: boolean = false;
  constructor(
    private obtener: ObtenerObjetosService,
    private registro: RegistroService
  ) {
    this.username = sessionStorage.getItem('username')!;
    this.obtener.ObtenerAnunciosAprobados().subscribe(
      (respuesta: Anuncio[]) => {
        this.anuncios = respuesta;
      },
      (error: any) => {
        this.espera = true;
      }
    );
  }
  ngOnInit(): void {}
}
