import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  Validators,
  FormBuilder,
  ReactiveFormsModule,
} from '@angular/forms';
import { CommonModule, formatCurrency } from '@angular/common';
import { AnuncioService } from 'app/Servicios/Anuncios/anuncio.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-crear-anuncio',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './crear-anuncio.component.html',
  styleUrl: './crear-anuncio.component.css',
})
export class CrearAnuncioComponent implements OnInit {
  validarForm!: FormGroup;
  foto: File | null = null;
  tipoAnuncio: number = 1;
  nombreAnunciante: string | null = null;
  mostrarFoto: SafeUrl | null = null;
  anuncioFoto: boolean = false;
  anuncio: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private anuncioService: AnuncioService,
    private sanitizer: DomSanitizer
  ) {
    this.nombreAnunciante = sessionStorage.getItem('username');
    this.validarForm = this.formBuilder.group({
      tipo: ['1', Validators.required],
      texto: [
        '',
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(500),
        ],
      ],
      archivo: [{ value: null, disabled: true }],
      video: [{ value: '', disabled: true }],
      dias: [1, [Validators.required, Validators.min(1)]],
      fecha: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.cambioTipoAnuncio();

    this.validarForm.get('tipo')?.valueChanges.subscribe(() => {
      this.cambioTipoAnuncio();
    });
  }

  obtenerImagen(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.foto = input.files[0];

      const reader = new FileReader();
      reader.readAsDataURL(this.foto);
      reader.onload = () => {
        this.mostrarFoto = this.sanitizer.bypassSecurityTrustUrl(
          reader.result as string
        );
        console.log('Preview de la imagen:', this.mostrarFoto);
      };
    }
  }

  cambioTipoAnuncio() {
    const tipoAnuncio = this.validarForm.get('tipo')?.value; // Obtener el valor actual del tipo

    this.validarForm.get('archivo')?.disable();
    this.validarForm.get('video')?.disable();
    this.validarForm.get('texto')?.enable();
    switch (tipoAnuncio) {
      case '1':
        break;
      case '2':
        this.validarForm.get('archivo')?.enable();
        break;
      case '3':
        this.validarForm.get('video')?.enable();
        break;
    }
  }

  registrarAnuncio() {
    if (this.validarForm.invalid) {
      console.warn('Formulario no válido');
      return;
    }

    const tipoAnuncio = this.validarForm.get('tipo')?.value;
    const diasDuracion = this.validarForm.get('dias')?.value;
    const fechaInicio = this.validarForm.get('fecha')?.value;

    const anuncioData = new FormData();
    anuncioData.append('tipo', tipoAnuncio);
    anuncioData.append('nombreAnunciante', this.nombreAnunciante || '');
    anuncioData.append('diasDuracion', diasDuracion.toString());
    anuncioData.append('fechaInicio', fechaInicio);

    switch (tipoAnuncio) {
      case '1':
        anuncioData.append('texto', this.validarForm.get('texto')?.value || '');
        break;

      case '2':
        anuncioData.append('texto', this.validarForm.get('texto')?.value || '');
        if (this.foto) {
          anuncioData.append('imagen', this.foto, this.foto.name);
        }
        break;

      case '3': 
        anuncioData.append(
          'urlVideo',
          this.validarForm.get('video')?.value || ''
        );
        break;

      default:
        console.error('Tipo de anuncio no reconocido');
        return;
    }

    this.anuncioService.registrarAnuncio(anuncioData).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Anuncio registrado',
          text: 'El anuncio se ha registrado exitosamente',
          confirmButtonText: 'Aceptar',
        }).then(() => {
          this.validarForm.reset();
        });
      },
      error: (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Error al registrar',
          text: 'Hubo un error al registrar el anuncio, verifica tu cartera',
          confirmButtonText: 'Aceptar',
        }).then(() => {
          this.validarForm.reset();
        });
      },
    });
  }
}
