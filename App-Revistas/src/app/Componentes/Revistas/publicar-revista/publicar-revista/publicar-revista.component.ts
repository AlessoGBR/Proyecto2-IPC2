import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { SeleccionEtiquetasComponent } from 'app/Componentes/Seleccion-etiquetas/selecion-etiquetas/seleccion-etiquetas/seleccion-etiquetas.component';
import { Etiqueta } from 'app/Objetos/Etiqueta';
import { Revista } from 'app/Objetos/Revista';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';
import { MenuEditorComponent } from 'app/Componentes/Menus/menu-editor/menu-editor/menu-editor.component';
import { AlmacenamientoService } from 'app/Servicios/Revistas/almacenamiento-service/almacenamiento-service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-publicar-revista',
  standalone: true,
  imports: [
    SeleccionEtiquetasComponent,
    ReactiveFormsModule,
    MenuEditorComponent,
  ],
  templateUrl: './publicar-revista.component.html',
  styleUrl: './publicar-revista.component.css',
})
export class PublicarRevistaComponent implements OnInit {
  crearEtiqueta: boolean = true;
  validarForm!: FormGroup;
  error: boolean = false;
  anuncios: boolean = false;

  etiquetas: Etiqueta[] | null = null;
  archivo!: File;
  esGratuita: boolean = false;
  usuarioCreador: string | null = null;

  constructor(
    private registrar: RegistroService,
    private almacenamiento: AlmacenamientoService,
    private builder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.validarForm = this.builder.group({
      titulo: new FormControl('', Validators.required),
      version: new FormControl('', Validators.required),
      descripcion: new FormControl('', Validators.required),
      archivo: new FormControl('', Validators.required),
      fecha: new FormControl('', Validators.required),
      suscripciones: new FormControl(false),
      tieneComentarios: new FormControl(false),
      tieneReacciones: new FormControl(false),
      tieneAnuncios: new FormControl(false),
      precioSuscripcion: new FormControl({ value: 0, disabled: true }),
    });
  }

  recibirEtiquetas(etiqueta: Etiqueta[]) {
    this.etiquetas = etiqueta;
  }

  obtenerPath(event: Event) {
    const files = (event.target as HTMLInputElement).files;
    const file = files?.item(0);

    if (file && file.type === 'application/pdf') {
      this.archivo = file;
      console.log('Archivo PDF seleccionado:', this.archivo);
    } else {
      console.warn('Por favor, seleccione un archivo PDF.');
    }
  }

  crearPublicacion() {
    if (
      this.validarForm.valid &&
      this.etiquetas &&
      this.etiquetas.length >= 1
    ) {
      this.usuarioCreador = sessionStorage.getItem('username');

      if (this.validarForm.value.tieneAnuncios) {
        this.continueWithProcess();
      } else {
        this.registrar.verificarCartear(true, this.usuarioCreador!).subscribe(
          () => {
            this.continueWithProcess();
          },
          (error: any) => {
            Swal.fire({
              icon: 'error',
              title: 'Sin fondos',
              text: 'No tienes fondos suficientes para no poner anuncios.',
              confirmButtonText: 'Aceptar',
            });
            this.validarForm.reset();
          }
        );
      }
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Error en el formulario',
        text: 'Por favor completa todos los campos requeridos.',
        confirmButtonText: 'Aceptar',
      });
      this.validarForm.reset();
    }
  }

  continueWithProcess() {
    const suscripciones = this.validarForm.value.suscripciones || false;
    const tieneComentarios = this.validarForm.value.tieneComentarios || false;
    const tieneReacciones = this.validarForm.value.tieneReacciones || false;
    const tieneAnuncios = this.validarForm.value.tieneAnuncios || false;

    const revista = {
      titulo: this.validarForm.value.titulo,
      descripcion: this.validarForm.value.descripcion,
      version: this.validarForm.value.version,
      suscripciones: suscripciones,
      tieneComentarios: tieneComentarios,
      tieneReacciones: tieneReacciones,
      tieneAnuncios: tieneAnuncios,
      fecha: this.validarForm.value.fecha,
      usuarioCreador: this.usuarioCreador,
      etiquetas: this.etiquetas,
    };

    const formData = new FormData();
    formData.append(
      'revista',
      new Blob([JSON.stringify(revista)], { type: 'application/json' })
    );
    formData.append('archivo', this.archivo);
    this.registrar.registrarRevista(formData).subscribe(
      () => {
        Swal.fire({
          icon: 'success',
          title: 'Revista registrada correctamente',
          text: 'La revista se ha registrado con éxito.',
          confirmButtonText: 'Aceptar',
        });
        this.validarForm.reset();
      },
      (error: any) => {
        Swal.fire({
          icon: 'error',
          title: 'No se pudo crear la revista',
          text: 'Ocurrió un error, por favor intenta nuevamente.',
          confirmButtonText: 'Aceptar',
        });
        this.validarForm.reset();
      }
    );
  }
}
