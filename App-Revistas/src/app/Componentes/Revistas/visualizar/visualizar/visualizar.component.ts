import { Component, OnInit, Input } from '@angular/core';
import { Revista } from 'app/Objetos/Revista';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { SafeResourceUrl } from '@angular/platform-browser';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { VerRevistaComponent } from '../../ver-revista/ver-revista.component';
import { FormsModule } from '@angular/forms';
import { AnuncioComponent } from "../../../anuncio/anuncio/anuncio.component";
import { ComentarioService } from 'app/Servicios/Revistas/comentario-service/comentario-service.service';
import { ComentarioComponent } from '../../comentario/comentario/comentario.component';
import { Comentario } from 'app/Objetos/Comentario';
import { RegistroService } from 'app/Servicios/Revistas/registro-service/registro.service';
import { Reaccion } from 'app/Objetos/Reaccion';
import { ReaccionService } from 'app/Servicios/Revistas/reaccion-service/reaccion-service.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-visualizar',
  standalone: true,
  imports: [CommonModule, VerRevistaComponent, AnuncioComponent, ComentarioComponent, FormsModule, RouterLink],
  templateUrl: './visualizar.component.html',
  styleUrl: './visualizar.component.css',
})
export class VisualizarComponent implements OnInit {
  nombreUsuario: string | null = null;
  idRevista!: string;
  espera: boolean = true;
  revista!: Revista;
  urlDescarga!: SafeResourceUrl;
  reacciones: Reaccion[] = [];
  @Input() path: string = '';
  sanitizedUrl: SafeResourceUrl | null = null;
  comentarios: Comentario[] = [];
  cantidadCom: number = 0;
  cantidadMG: number = 0;
  username: string | null = null;
  comentarioNuevo!: string;
  tieneMG: boolean = false;

  constructor(
    private ruta: ActivatedRoute,
    private obtener: ObtenerObjetosService,
    private obtenerComentario: ComentarioService,
    private registro: RegistroService,
    private obtenerReaccion: ReaccionService
  ) {
    let idRevista = this.ruta.snapshot.paramMap.get('idRevista');
    if (idRevista != null) {
      this.nombreUsuario = sessionStorage.getItem('username');
      this.idRevista = idRevista;
    }
  }

  ngOnInit(): void {
    this.obtener.obtenerRevista(this.idRevista).subscribe(
      (revista: Revista) => {
        this.revista = revista;
        this.espera = false;
        this.path = revista.revistaPath!;
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
      },
      (error: any) => {
        this.espera = true;
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

    darMeGusta(): void {
      this.cambiarBoton();
      this.registrarReaccion();
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
}
