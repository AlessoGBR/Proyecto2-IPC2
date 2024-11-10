import { Component, OnInit, NgModule } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Revista } from 'app/Objetos/Revista';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Anunciante } from 'app/Objetos/Anunciante';
import { Usuario } from 'app/Objetos/Usuario';
import { Backend } from 'app/Objetos/Backend';

@Component({
  selector: 'app-reporte-editor',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './reporte-editor.component.html',
  styleUrl: './reporte-editor.component.css'
})
export class ReporteEditorComponent implements OnInit {

  revistas!: Revista[];
  anunciantes!: Anunciante[];
  usuario: string;
  idRevista: number = 0;
  fecha_inicio: string = "vacio";
  fecha_final: string = "vacio";
  opcionReporte: number = 1;
  anunciante: string = "vacio";

  //Previsualizar reporte
  path!: string;
  url!: SafeResourceUrl;

  constructor(
    private obtener: ObtenerObjetosService,
    private sanitizer: DomSanitizer
  ) { 
    this.usuario = sessionStorage.getItem('username')!;
    this.obtener.obtenerRevistasAprobadas().subscribe((respuesta: Revista[]) => {
      this.revistas = respuesta;
      this.obtener.obtenerAnunciantes().subscribe((respuesta: Anunciante[]) => {
        this.anunciantes = respuesta;
      });
    },
      (error: any) => {
      }
    );
  }

  ngOnInit(): void {
  }

  cambioReporte(selec: any) {
    this.opcionReporte = selec;
  }

  cambioRevista(selec: any) {
    this.idRevista = selec;
  }

  cambioAnun(cambio: string) {
    this.anunciante = cambio;
  }

  obtenerLink() {
    let url = Backend.Path + "EditorReportes?opcionReporte=" + this.opcionReporte;
    url += "&fecha_inicio=" + this.fecha_inicio;
    url += "&fecha_final=" + this.fecha_final;
    url += "&usuario=" + this.usuario;
    url += "&idRevista=" + this.idRevista;
    this.url = this.sanitizer.bypassSecurityTrustResourceUrl(url); 
  }

  generarReporte() {
    if (this.fecha_final == "" || this.fecha_inicio == "") {
      this.fecha_final = "vacio";
      this.fecha_inicio = "vacio";
    }
    if (this.opcionReporte == 4 || this.opcionReporte == 5 || this.opcionReporte == 2 || this.opcionReporte == 6 || this.opcionReporte == 7) {
      this.idRevista = 0;
    }
    if (this.opcionReporte != 2) {
      this.anunciante = "vacio";
    }
    this.obtenerLink();
  }
}
