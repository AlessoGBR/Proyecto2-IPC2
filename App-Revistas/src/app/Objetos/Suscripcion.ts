export class Suscripcion {
  idSuscripcion: number;
  fecha: Date;
  nombre_usuario: string;
  idRevista: number;

  constructor(
    idSuscripcion: number,
    fecha: Date,
    nombre_usuario: string,
    idRevista: number
  ) {
    this.idSuscripcion = idSuscripcion;
    this.fecha = fecha;
    this.nombre_usuario = nombre_usuario;
    this.idRevista = idRevista;
  }
}
