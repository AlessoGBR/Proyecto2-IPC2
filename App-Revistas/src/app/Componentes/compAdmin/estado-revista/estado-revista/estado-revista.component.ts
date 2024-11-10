import { Component, OnInit } from '@angular/core';
import { Revista } from 'app/Objetos/Revista';
import { EditarRevistaComponent } from 'app/Componentes/Revistas/editar-revista/editar-revista/editar-revista.component';
import { VerRevistaComponent } from 'app/Componentes/Revistas/ver-revista/ver-revista.component';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-estado-revista',
  standalone: true,
  imports: [
    EditarRevistaComponent,
    VerRevistaComponent,
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './estado-revista.component.html',
  styleUrl: './estado-revista.component.css',
})
export class EstadoRevistaComponent implements OnInit {
  espera: boolean = false;
  validarForm: FormGroup;
  revista!: Revista;
  idRevista!: string;
  estado = false;

  constructor(
    private ruta: ActivatedRoute,
    private obtener: ObtenerObjetosService,
    private builder: FormBuilder,
    private registrar: RegistroService,
    private router: Router
  ) {
    let temporal = this.ruta.snapshot.paramMap.get('idRevista');
    if (temporal != null) {
      this.idRevista = temporal;
    }
    this.validarForm = this.builder.group({
      costo: new FormControl('1', Validators.required),
      estado: new FormControl('1', Validators.required),
      fecha: new FormControl('2021-10-25', Validators.required),
    });
  }

  ngOnInit(): void {
    this.obtener.obtenerRevista(this.idRevista).subscribe(
      (respuesta: Revista) => {
        this.revista = respuesta;
      },
      (error: any) => {}
    );
  }

  cambiarEstadoRevista() {
    console.log(this.validarForm.value.estado);
    if (this.validarForm.valid) {
      this.revista.fecha = this.validarForm.value.fecha;
      if (this.validarForm.value.estado == 1) {
        this.revista.aprobada = true;
        this.revista.precio = this.validarForm.value.costo;
        console.log(this.revista.precio);
        if (this.revista.idRevista === undefined) {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo aprobar la revista, intenta nuevamente',
            confirmButtonText: 'Aceptar',
          }).then(() => {
            this.router.navigate(['/InicioAdmin']);
          });
          return;
        }
        this.registrar
          .aceptarRevista(this.revista.idRevista, this.revista.precio)
          .subscribe({
            next: (response) => {
              Swal.fire({
                icon: 'success',
                title: 'Aprobacion',
                text: 'Revista aprobada exitosamente',
                confirmButtonText: 'Aceptar',
              }).then(() => {
                this.router.navigate(['/InicioAdmin']);
              });
            },
            error: (error) => {
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'No se pudo aprobar la revista, intenta nuevamente',
                confirmButtonText: 'Aceptar',
              }).then(() => {
                this.router.navigate(['/InicioAdmin']);
              });
            },
          });
      } else {
        this.revista.aprobada = false;
        if (this.revista.idRevista === undefined) {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo aprobar la revista, intenta nuevamente',
            confirmButtonText: 'Aceptar',
          }).then(() => {
            this.router.navigate(['/InicioAdmin']);
          });
          return;
        }
        this.registrar.denegarRevista(this.revista.idRevista, -1).subscribe({
          next: (response) => {
            Swal.fire({
              icon: 'success',
              title: 'Denegada',
              text: 'Revista denegada exitosamente',
              confirmButtonText: 'Aceptar',
            }).then(() => {
              this.router.navigate(['/InicioAdmin']);
            });
          },
          error: (error) => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo denegar la revista, intenta nuevamente',
              confirmButtonText: 'Aceptar',
            }).then(() => {
              this.router.navigate(['/InicioAdmin']);
            });
          },
        });
      }
    }
  }
}
