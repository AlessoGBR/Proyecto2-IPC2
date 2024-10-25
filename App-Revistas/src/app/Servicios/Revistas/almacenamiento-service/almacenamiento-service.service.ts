import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlmacenamientoService{

  private usuarioKey = 'usuarioActual'; 

  constructor() { }

  guardarUsuario(usuario: any): void {
    localStorage.setItem(this.usuarioKey, JSON.stringify(usuario));
  }

  obtenerUsuario(): any {
    const usuario = localStorage.getItem(this.usuarioKey);
    return usuario ? JSON.parse(usuario) : null;
  }

  eliminarUsuario(): void {
    localStorage.removeItem(this.usuarioKey);
  }

  estaLogueado(): boolean {
    return !!localStorage.getItem(this.usuarioKey);
  }
}
