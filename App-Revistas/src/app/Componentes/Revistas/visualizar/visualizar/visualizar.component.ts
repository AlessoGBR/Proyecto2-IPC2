import { Component, OnInit, Input } from '@angular/core';
import { Revista } from 'app/Objetos/Revista';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { VerRevistaComponent } from '../../ver-revista/ver-revista.component';
import { RouterLink } from '@angular/router';
import { AnuncioComponent } from "../../../anuncio/anuncio/anuncio.component";

@Component({
  selector: 'app-visualizar',
  standalone: true,
  imports: [CommonModule, VerRevistaComponent, RouterLink, AnuncioComponent],
  templateUrl: './visualizar.component.html',
  styleUrl: './visualizar.component.css',
})
export class VisualizarComponent implements OnInit {
  nombreUsuario: string | null = null;
  idRevista!: string;
  espera: boolean = true;
  revista!: Revista;
  urlDescarga!: SafeResourceUrl;
  @Input() path: string = '';
  sanitizedUrl: SafeResourceUrl | null = null;

  constructor(
    private ruta: ActivatedRoute,
    private obtener: ObtenerObjetosService,
  ) {
    let idRevista = this.ruta.snapshot.paramMap.get('idRevista');
    if (idRevista != null) {
      this.nombreUsuario = sessionStorage.getItem('username');
      this.idRevista = idRevista;
    }
  }

  ngOnInit(): void {
    this.obtener.obtenerRevista(this.idRevista).subscribe(
      (revista: Revista) => {
        this.revista = revista;
        this.espera = false;
        this.path = revista.revistaPath!;
      },
      (error: any) => {
        this.espera = true;
      }
    );
  }
}
