export class Suscripcion {
  fecha: string;
  nombre_usuario: string;
  idRevista: number;

  constructor(
    fecha: string,
    nombre_usuario: string,
    idRevista: number
  ) {
    this.fecha = fecha;
    this.nombre_usuario = nombre_usuario;
    this.idRevista = idRevista;
  }
}
