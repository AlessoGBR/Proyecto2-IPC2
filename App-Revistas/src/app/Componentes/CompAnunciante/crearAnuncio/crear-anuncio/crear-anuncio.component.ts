import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, formatCurrency } from '@angular/common';
import { AnuncioService } from 'app/Servicios/Anuncios/anuncio.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-crear-anuncio',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './crear-anuncio.component.html',
  styleUrl: './crear-anuncio.component.css'
})
export class CrearAnuncioComponent implements OnInit {

  validarForm!: FormGroup;
  foto: File | null = null;
  tipoAnuncio: number = 1;
  nombreAnunciante: string| null = null;
  mostrarFoto: string | null = null;
  anuncioFoto: boolean = false;
  anuncio: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private anuncioService: AnuncioService
  ) {
    this.nombreAnunciante = sessionStorage.getItem('username');
    this.validarForm = this.formBuilder.group({
      tipo: ['1', Validators.required],
      texto: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(500)]],
      archivo: [{ value: null, disabled: true }],
      video: [{ value: '', disabled: true }],
      dias: [1, [Validators.required, Validators.min(1)]],
      fecha: ['', Validators.required]
    });
  }

  ngOnInit(): void {    
    this.cambioTipoAnuncio();

    this.validarForm.get('tipo')?.valueChanges.subscribe(() => {
      this.cambioTipoAnuncio();
    });
  }

  obtenerImagen(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      const reader = new FileReader();
      reader.onload = () => {
        this.mostrarFoto = reader.result as string;
      };
      reader.readAsDataURL(input.files[0]);
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
      console.log('Formulario no válido');
      return;
    }
    const tipoAnuncio = this.validarForm.get('tipo')!.value;
    const diasDuracion = this.validarForm.get('dias')!.value;
    const fechaInicio = this.validarForm.get('fecha')!.value;
    const anuncioData: any = { 
        tipo: tipoAnuncio,
        nombreAnunciante: this.nombreAnunciante,
        diasDuracion: diasDuracion,
        fechaInicio: fechaInicio 
    };

    if (tipoAnuncio === '1') {
        anuncioData.texto = this.validarForm.get('texto')!.value;
    } else if (tipoAnuncio === '2') {
        anuncioData.texto = this.validarForm.get('texto')!.value;
        anuncioData.imagen = this.validarForm.get('archivo')!.value;
    } else if (tipoAnuncio === '3') {
        anuncioData.urlVideo = this.validarForm.get('video')!.value;
    }

    this.anuncioService.registrarAnuncio(anuncioData).subscribe({
        next: response => {
            Swal.fire({
                icon: 'success',
                title: 'Anuncio registrado',
                text: 'El anuncio se ha registrado exitosamente',
                confirmButtonText: 'Aceptar'
            });
            console.log('Anuncio registrado exitosamente', response);
        },
        error: err => {
            Swal.fire({
                icon: 'error',
                title: 'Error al registrar',
                text: 'Hubo un error al registrar el anuncio',
                confirmButtonText: 'Aceptar'
            });
            console.error('Error al registrar el anuncio', err);
        }
    });
  }

}
