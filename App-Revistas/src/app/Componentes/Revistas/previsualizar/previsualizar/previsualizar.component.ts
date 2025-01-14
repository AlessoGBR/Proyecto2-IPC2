import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Reaccion } from 'app/Objetos/Reaccion';
import { Comentario } from 'app/Objetos/Comentario';
import { ReaccionService } from 'app/Servicios/Revistas/reaccion-service/reaccion-service.service';
import { ComentarioService } from 'app/Servicios/Revistas/comentario-service/comentario-service.service';
import { ComentarioComponent } from '../../comentario/comentario/comentario.component';
import { RouterLink, ActivatedRoute } from '@angular/router';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { Revista } from 'app/Objetos/Revista';
import { FormsModule } from '@angular/forms';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';
import { Suscripcion } from 'app/Objetos/Suscripcion';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-previsualizar',
  standalone: true,
  imports: [CommonModule, FormsModule, ComentarioComponent, RouterLink],
  templateUrl: './previsualizar.component.html',
  styleUrl: './previsualizar.component.css',
})
export class PrevisualizarComponent implements OnInit {
  revista!: Revista;
  tieneMG: boolean = false;
  comentarios: Comentario[] = [];
  cantidadMG: number = 0;
  cantidadCom: number = 0;
  reacciones: Reaccion[] = [];
  comentarioNuevo!: string;
  tieneSub!: boolean;
  username: string | null = null;
  idRevista: string | null = null;

  constructor(
    private obtenerReaccion: ReaccionService,
    private obtenerComentario: ComentarioService,
    private obtener: ObtenerObjetosService,
    private registro: RegistroService,
    private route: ActivatedRoute
  ) {
    this.idRevista = this.route.snapshot.paramMap.get('idRevista');
    this.route.paramMap.subscribe((params) => {
      this.idRevista = params.get('idRevista');
    });
  }

  ngOnInit(): void {
    if (this.idRevista != null) {
      this.obtener.obtenerRevista(this.idRevista).subscribe(
        (revista: Revista) => {
          if (revista) {
            this.revista = revista;
            this.obtenerReaccion
              .obtenerReacciones(this.revista.idRevista!)
              .subscribe(
                (respuesta: Reaccion[]) => {
                  this.reacciones = respuesta;
                  this.verificarMegustaUsuario();
                  this.obtenerComentario
                    .obtenerComentarios(this.revista.idRevista!)
                    .subscribe(
                      (respuesta: Comentario[]) => {
                        this.comentarios = respuesta;
                        this.cantidadCom = this.comentarios.length;
                        this.username = sessionStorage.getItem('username');
                        this.obtener.obtenerSuscripciones(this.username!, this.idRevista!).subscribe(
                          (respuesta: boolean) => {
                            // Asignamos el valor de respuesta directamente a tieneSub
                            this.tieneSub = respuesta;
                          },
                          (error) => {
                            // Si ocurre un error, establecemos tieneSub como false
                            this.tieneSub = false;
                          }
                        );
                      },
                      (error: any) => {
                        console.error('Error al obtener comentarios:', error);
                      }
                    );
                },
                (error: any) => {
                  console.error('Error al obtener reacciones:', error);
                }
              );
          } else {
            console.error('No se encontró la revista con id:', this.idRevista);
          }
        },
        (error: any) => {
          console.error('Error al obtener la revista:', error);
        }
      );
    }
  }

  obtenerReacciones(): void {
    this.obtenerReaccion.obtenerReacciones(this.revista.idRevista!).subscribe(
      (respuesta: Reaccion[]) => {
        this.reacciones = respuesta;
        this.verificarMegustaUsuario();
      },
      (error) => {
        console.error('Error al obtener las reacciones', error);
      }
    );
  }

  obtenerComentarios(): void {
    this.obtenerComentario
      .obtenerComentarios(this.revista.idRevista!)
      .subscribe(
        (respuesta: Comentario[]) => {
          this.comentarios = respuesta;
          this.cantidadCom = this.comentarios.length;
        },
        (error) => {
          console.error('Error al obtener los comentarios', error);
        }
      );
  }

  darMeGusta(): void {
    this.cambiarBoton();
    this.registrarReaccion();
  }

  verificarMegustaUsuario(): void {
    const nombre = sessionStorage.getItem('username');

    this.cantidadMG = this.reacciones.filter((el) => el.reaccion).length;

    const reaccionUsuario = this.reacciones.find(
      (el) => el.nombreUsuario === nombre
    );
    this.tieneMG = !!(reaccionUsuario && reaccionUsuario.reaccion);
  }

  cambiarBoton(): void {
    if (this.tieneMG) {
      this.cantidadMG -= 1;
    } else {
      this.cantidadMG += 1;
    }

    this.tieneMG = !this.tieneMG;
  }

  registrarReaccion(): void {
    const fechaActual = new Date().toISOString().split('T')[0];

    const reaccion = new Reaccion(
      this.revista.idRevista!,
      this.tieneMG,
      fechaActual,
      (this.username = sessionStorage.getItem('username'))
    );

    this.obtenerReaccion.registrarReaccion(reaccion).subscribe(
      () => {
        if (this.tieneMG) {
          Swal.fire({
            icon: 'success',
            title: 'Me Gusta',
            text: 'Se ha registrado tu reaccion.',
            confirmButtonText: 'Aceptar',
          });
        } else {
          Swal.fire({
            icon: 'success',
            title: 'Me Gusta',
            text: 'Se ha quitado la reaccion.',
            confirmButtonText: 'Aceptar',
          });
        }
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Me Gusta',
          text: 'Ocurrió un error, por favor intenta nuevamente.',
          confirmButtonText: 'Aceptar',
        });
        this.cambiarBoton();
      }
    );
  }

  hacerComentario() {
    console.log(this.revista.idRevista);
    console.log(this.username);
    if (!this.username || !this.comentarioNuevo.trim()) {
      console.warn('Debe iniciar sesión y escribir un comentario.');
      return;
    }

    const comentario = new Comentario(
      this.revista.idRevista!,
      this.comentarioNuevo.trim(),
      this.username
    );

    this.registro.registrarComentario(comentario).subscribe({
      next: () => {
        this.comentarios.push(comentario);

        this.comentarioNuevo = '';

        Swal.fire({
          icon: 'success',
          title: 'Comentario',
          text: 'Se ha registrado tu comentario.',
          confirmButtonText: 'Aceptar',
        });
      },
      error: (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Comentarios',
          text: 'Ocurrió un error, por favor intenta nuevamente.',
          confirmButtonText: 'Aceptar',
        });
      },
    });
  }

  Suscribirse() {
    const fechaActual = new Date().toISOString().split('T')[0];
    if (!this.username) {
      return;
    }
    const suscripcion = new Suscripcion(
      fechaActual,
      this.username,
      this.revista.idRevista!
    );

    this.registro.registrarSuscripcion(suscripcion).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Suscripcion',
          text: 'Te haz suscrito a la revista.',
          confirmButtonText: 'Aceptar',
        }).then(() => {
          window.location.reload();
        });
      },
      error: (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Suscripcion',
          text: 'Ocurrió un error, por favor intenta nuevamente.',
          confirmButtonText: 'Aceptar',
        });
      },
    });
  }

  Dessuscribirse() {
    const fechaActual = new Date().toISOString().split('T')[0];
    if (!this.username) {
      return;
    }
    const suscripcion = new Suscripcion(
      fechaActual,
      this.username,
      this.revista.idRevista!
    );

    this.registro.anularSuscripcion(suscripcion).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Suscripcion',
          text: 'Te haz desuscripto de la revista.',
          confirmButtonText: 'Aceptar',
        }).then(() => {
          window.location.reload();
        });
      },
      error: (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Suscripcion',
          text: 'Ocurrió un error, por favor intenta nuevamente.',
          confirmButtonText: 'Aceptar',
        });
      },
    });
  }
}
