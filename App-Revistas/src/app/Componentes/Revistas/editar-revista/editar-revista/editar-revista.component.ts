import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, } from '@angular/forms';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { ActivatedRoute } from '@angular/router';
import { Revista } from 'app/Objetos/Revista';

@Component({
  selector: 'app-editar-revista',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './editar-revista.component.html',
  styleUrl: './editar-revista.component.css',
})
export class EditarRevistaComponent implements OnInit {
  revista!: Revista;
  @Input() idRevista!: string;
  validarForm!: FormGroup;
  editable: boolean = true;
  constructor(
    private builder: FormBuilder,
    private ruta: ActivatedRoute,
    private obtener: ObtenerObjetosService
  ) {
    let temporal = this.ruta.snapshot.paramMap.get('idRevista');
    if (temporal != null) {
      this.idRevista = temporal;
      this.obtener
        .obtenerRevista(this.idRevista)
        .subscribe((respuesta: Revista) => {
          this.revista = respuesta;
        });
    } else {
      this.obtener
        .obtenerRevista(this.idRevista)
        .subscribe((respuesta: Revista) => {
          this.revista = respuesta;
        });
    }
  }

  ngOnInit(): void {
    if (this.revista != null) {
      this.validarForm = this.builder.group({
        titulo: [
          { value: this.revista.titulo, disabled: this.editable },
          Validators.required,
        ],
        version: [
          { value: this.revista.version, disabled: this.editable },
          Validators.required,
        ],
        descripcion: [
          { value: this.revista.descripcion, disabled: this.editable },
          Validators.required,
        ],
        fecha: [
          { value: this.revista.fecha, disabled: this.editable },
          Validators.required,
        ],
        suscripciones: [
          { value: this.revista.suscripciones, disabled: this.editable },
          Validators.required,
        ],
        tiene_comentarios: [
          { value: this.revista.tieneComentarios, disabled: this.editable },
          Validators.required,
        ],
        tiene_reaccion: [
          { value: this.revista.tieneReacciones, disabled: this.editable },
          Validators.required,
        ],
      });
    } else {
      this.validarForm = this.builder.group({
        titulo: [, Validators.required],
        version: [, Validators.required],
        descripcion: [, Validators.required],
        fecha: [, Validators.required],
        es_pago: [, Validators.required],
        suscripciones: [, Validators.required],
        precioSub: [, Validators.required],
        tiene_comentarios: [, Validators.required],
        tiene_reaccion: [, Validators.required],
      });
    }
  }
}
