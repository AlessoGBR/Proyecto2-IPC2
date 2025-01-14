import { Component , OnInit} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-precio-revista',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './precio-revista.component.html',
  styleUrl: './precio-revista.component.css'
})
export class PrecioRevistaComponent implements OnInit {
revistaForm: FormGroup;
  usuario: string | null = null;
  total!: number;
  precioR: number = 0;

  constructor(private formBuilder: FormBuilder,
    private registro: RegistroService
  ) {
    this.usuario = sessionStorage.getItem('username'); 

    this.registro.preciosRevista().subscribe({
      next: (precios) => {
        this.revistaForm.patchValue({
          precio: precios.precio
        });
      },
      error: (error) => {
        console.error('Error al obtener los precios:', error);
      },
    });
    this.revistaForm = this.formBuilder.group({
      precio: [this.precioR, [Validators.required, Validators.min(1)]]
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

  actualizarPrecios(precios: { precio: number}): void {
    this.registro.actualizarPrecioRevistas(precios).subscribe({
        next: (response) => {
          Swal.fire({
            icon: 'success',
            title: 'Precio',
            text: 'Se ha modificado el precio',
            confirmButtonText: 'Aceptar',
          }).then(() => {
            window.location.reload();
          });          
        },
        error: (err) => {
          Swal.fire({
            icon: 'error',
            title: 'Error al modificar',
            text: 'No se pudo modificar el precio',
            confirmButtonText: 'Aceptar',
          }).then(() => {
            window.location.reload();
          });  
        },
      });
  }
}
