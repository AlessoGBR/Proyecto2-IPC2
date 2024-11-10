import { Component, Input, OnInit } from '@angular/core';
import { Comentario } from 'app/Objetos/Comentario';
import { Reaccion } from 'app/Objetos/Reaccion';
import { ReaccionService } from 'app/Servicios/Revistas/reaccion-service/reaccion-service.service';
import { ComentarioService } from 'app/Servicios/Revistas/comentario-service/comentario-service.service';
import { RouterLink, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-tarjeta-revista',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './tarjeta-revista.component.html',
  styleUrl: './tarjeta-revista.component.css'
})
export class TarjetaRevistaComponent implements OnInit {
  @Input() revista: any; 
  tieneMG: boolean = false;
  cantidadMG: number = 0;
  cantidadCom: number = 0;
  reacciones: Reaccion[] = [];
  comentarios: Comentario[] = [];
  username: String| null = null;
  idRevista: string | null = null;

  constructor(
    private obtenerReaccion: ReaccionService,
    private obtenerComentario: ComentarioService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.idRevista = this.route.snapshot.paramMap.get('idRevista');

    this.route.paramMap.subscribe((params: { get: (arg0: string) => string | null; }) => {
      this.idRevista = params.get('idRevista');
      if (this.revista?.idRevista != null) {
        this.obtenerReacciones();
        this.obtenerComentarios();
      }
    });
    
  }

  obtenerReacciones(): void {
    this.obtenerReaccion.obtenerReacciones(this.revista.idRevista).subscribe((respuesta: Reaccion[]) => {
      this.reacciones = respuesta;
      this.verificarMegustaUsuario();
    }, error => {
      console.error("Error al obtener las reacciones", error);
    });
  }

  obtenerComentarios(): void {
    this.obtenerComentario.obtenerComentarios(this.revista.idRevista).subscribe((respuesta: Comentario[]) => {
      this.comentarios = respuesta;
      this.cantidadCom = this.comentarios.length;
    }, error => {
      console.error("Error al obtener los comentarios", error);
    });
  }

  verificarMegustaUsuario(): void {
    const nombre = sessionStorage.getItem('username');
    
    this.cantidadMG = this.reacciones.filter(el => el.reaccion).length;

    const reaccionUsuario = this.reacciones.find(el => el.nombreUsuario === nombre);
    this.tieneMG = !!(reaccionUsuario && reaccionUsuario.reaccion); 
  }

  darMeGusta(): void {
      this.cambiarBoton(); 
      this.registrarReaccion();
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
      this.revista.idRevista, 
      this.tieneMG, 
      fechaActual, 
      this.username = sessionStorage.getItem('username')
    );
  
    this.obtenerReaccion.registrarReaccion(reaccion).subscribe(() => {
      if(this.tieneMG){
        Swal.fire({
          icon: 'success',
          title: 'Me Gusta',
          text: 'Se ha registrado tu reaccion.',
          confirmButtonText: 'Aceptar'
        });
      } else {
        Swal.fire({
          icon: 'success',
          title: 'Me Gusta',
          text: 'Se ha quitado la reaccion.',
          confirmButtonText: 'Aceptar'
        });
      }
      
    }, error => {
      Swal.fire({
        icon: 'error',
        title: 'Me Gusta',
        text: 'Ocurrió un error, por favor intenta nuevamente.',
        confirmButtonText: 'Aceptar'
      });
      this.cambiarBoton(); 
    });
  }
  
}
