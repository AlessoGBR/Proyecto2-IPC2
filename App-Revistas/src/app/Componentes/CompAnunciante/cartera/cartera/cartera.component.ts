import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AnuncioService } from 'app/Servicios/Anuncios/anuncio.service';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cartera',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cartera.component.html',
  styleUrl: './cartera.component.css',
})
export class CarteraComponent implements OnInit {
  carteraForm: FormGroup;
  usuario: string | null = null;
  total!: number ;

  constructor(
    private formBuilder: FormBuilder,
    private anuncioService: AnuncioService
  ) {
    this.usuario = sessionStorage.getItem('username');
    this.carteraForm = this.formBuilder.group({
      monto: ['', [Validators.required, Validators.min(1)]],
    });
    this.anuncioService.totalCartera(this.usuario!).subscribe({
      next: (total: number) => {
        this.total = total;
      },
      error: (error) => {
        console.error('Error al obtener el total de la cartera:', error);
      },
    });
  }
  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.carteraForm.valid) {
      const monto = this.carteraForm.value['monto'];
      this.anuncioService.ingresoCartera(monto, this.usuario!).subscribe({
        next: (response) => {
          Swal.fire({
            icon: 'success',
            title: 'Cartera',
            text: 'Se ha ingresado el monto a la cartera',
            confirmButtonText: 'Aceptar',
          }).then(() => {
            window.location.reload();
          }); 
        },
        error: (err) => {
          Swal.fire({
            icon: 'error',
            title: 'Error al ingresar',
            text: 'No se pudo ingresar el monto a la cartera',
            confirmButtonText: 'Aceptar',
          }).then(() => {
            window.location.reload();
          }); 
        },
      });
      this.carteraForm.reset();
    }
  }
}
