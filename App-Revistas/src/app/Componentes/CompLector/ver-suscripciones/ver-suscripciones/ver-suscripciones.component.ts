import { Component, OnInit } from '@angular/core';
import { Revista } from 'app/Objetos/Revista';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { VerRevistasComponent } from 'app/Componentes/Revistas/ver-revistas/ver-revistas/ver-revistas.component';

@Component({
  selector: 'app-ver-suscripciones',
  standalone: true,
  imports: [VerRevistasComponent],
  templateUrl: './ver-suscripciones.component.html',
  styleUrl: './ver-suscripciones.component.css',
})
export class VerSuscripcionesComponent implements OnInit {
  revistas!: Revista[];
  usuario: string;
  espera: boolean = false;

  constructor(private obtener: ObtenerObjetosService) {
    this.usuario = sessionStorage.getItem('username')!;
    console.log(this.usuario)
  }

  ngOnInit(): void {
    this.obtener.obtenerRevistaSuscripciones(this.usuario).subscribe(
      (revistas: Revista[]) => {
        this.revistas = revistas;
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
