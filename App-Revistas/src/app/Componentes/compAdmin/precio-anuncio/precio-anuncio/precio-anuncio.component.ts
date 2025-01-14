import { Component , OnInit} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AnuncioService } from 'app/Servicios/Anuncios/anuncio.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-precio-anuncio',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './precio-anuncio.component.html',
  styleUrl: './precio-anuncio.component.css'
})
export class PrecioAnuncioComponent implements OnInit {

  revistaForm: FormGroup;
  usuario: string | null = null;
  total!: number;
  texto: number = 0;
  imagen: number = 0;
  video: number = 0;

  constructor(private formBuilder: FormBuilder,
    private registro: AnuncioService
  ) {
    this.usuario = sessionStorage.getItem('username'); 
    this.registro.preciosAnuncios().subscribe({
      next: (precios) => {
        this.revistaForm.patchValue({
          texto: precios.texto,
          imagen: precios.imagen,
          video: precios.video,
        });
      },
      error: (error) => {
        console.error('Error al obtener los precios:', error);
      },
    });
    this.revistaForm = this.formBuilder.group({
      texto: [this.texto, [Validators.required, Validators.min(1)]],
      imagen: [this.imagen, [Validators.required, Validators.min(1)]],
      video: [this.video, [Validators.required, Validators.min(1)]],
    }); 
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.revistaForm.valid) {
      const nuevosPrecios = this.revistaForm.value;
      this.actualizarPrecios(nuevosPrecios);
      this.revistaForm.patchValue(nuevosPrecios);
    } else {
      console.log('El formulario contiene errores.');
    }
  }

  actualizarPrecios(precios: { texto: number; imagen: number; video: number }): void {
    console.log('Enviando al servidor...', precios);
    this.registro.actualizarPrecioAnuncio(precios).subscribe({
        next: (response) => {
          Swal.fire({
            icon: 'success',
            title: 'Precios',
            text: 'Se han modificados los precios',
            confirmButtonText: 'Aceptar',
          }).then(() => {
            window.location.reload();
          });          
        },
        error: (err) => {
          Swal.fire({
            icon: 'error',
            title: 'Error al modificar',
            text: 'No se pudo modificar los precios',
            confirmButtonText: 'Aceptar',
          }).then(() => {
            window.location.reload();
          });  
        },
      });
  }
}
