import { Component, OnInit } from '@angular/core';
import { Anuncio } from 'app/Objetos/Anuncio';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { CommonModule } from '@angular/common';
import { VerImagenComponent } from 'app/Componentes/inicio/ver-imagen/ver-imagen/ver-imagen.component';


@Component({
  selector: 'app-anuncio',
  standalone: true,
  imports: [CommonModule,VerImagenComponent],
  templateUrl: './anuncio.component.html',
  styleUrl: './anuncio.component.css'
})
export class AnuncioComponent implements OnInit {

  anuncios!: Anuncio[];
  anuncio!: Anuncio;
  urlVideo!: SafeResourceUrl;

  constructor(
    private obtener: ObtenerObjetosService,
    private sanitizer: DomSanitizer,
  ) {

    this.obtener.obtenerAnunciosCliente().subscribe((respuesta: Anuncio[]) => {
      if (respuesta != null) {
        this.anuncios = respuesta;
        this.anuncioAleatorio();
      }

    });
  }
  ngOnInit(): void {
  }

  anuncioAleatorio() {
    let numero = this.obtenerRandom(0, this.anuncios.length);
    this.anuncio = this.anuncios[numero];
    if (this.anuncio.tipo == 'VIDEO') {
      this.urlVideo = this.obtenerLinkVideo(this.anuncio.urlVideo);
    }
  }

  obtenerRandom(min: number, max: number): number {
    return Math.floor(Math.random() * (max - min)) + min;
  }

  obtenerLinkVideo(url: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  
}
