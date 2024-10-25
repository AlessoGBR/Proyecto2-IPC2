import { Component, OnInit } from '@angular/core';
import { Anuncio } from 'app/Objetos/Anuncio';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ver-anuncios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ver-anuncios.component.html',
  styleUrl: './ver-anuncios.component.css'
})
export class VerAnunciosComponent implements OnInit {

  espera: boolean = false;
  anuncios!: Anuncio[];
  username!: string;

  constructor(
    private obtener: ObtenerObjetosService,    
  ) {
    this.username = sessionStorage.getItem('username')!
    this.obtener.obtenerAnuncios(this.username).subscribe((respuesta: Anuncio[]) => {
      this.anuncios = respuesta;
    },
      (error: any) => {
        this.espera = true;
      }
    );
  }


  ngOnInit(): void { }

  editarAnuncio(idAnuncio: number){
    
  }
}
