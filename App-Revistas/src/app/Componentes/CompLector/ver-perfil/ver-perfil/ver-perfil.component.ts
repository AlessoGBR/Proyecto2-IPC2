import { Component, OnInit } from '@angular/core';
import { ObtenerObjetosService } from 'app/Servicios/ObtenerObjetos/obtener-objetos.service';
import { Usuario } from 'app/Objetos/Usuario';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ver-perfil',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ver-perfil.component.html',
  styleUrl: './ver-perfil.component.css'
})
export class VerPerfilComponent implements OnInit{

  pathFoto!: string;
  username: string | null = null;
  usuario: Usuario | null = null;

  constructor(
    private obtener: ObtenerObjetosService
  ) {}

  ngOnInit(): void {
    this.username = sessionStorage.getItem('username');
    
    if (this.username) {
      this.obtener.obtenerPerfilUsuario(this.username).subscribe({
        next: (respuesta) => {
          this.usuario = respuesta;
          if (this.usuario && this.usuario.descripcion) {
            console.log(this.usuario.descripcion);
          } else {
            console.warn('El perfil del usuario no contiene descripción.');
          }
        },
        error: (error) => {
          console.error('Error al obtener el perfil del usuario:', error);
        }
      });
    } else {
      console.warn('No se encontró un nombre de usuario en la sesión.');
    }
  }
  
}
