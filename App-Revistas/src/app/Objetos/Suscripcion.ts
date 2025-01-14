export class Suscripcion {
  fecha: string;
  nombreUsuario: string;
  idRevista: number;

  constructor(
    fecha: string,
    nombreUsuario: string,
    idRevista: number
  ) {
    this.fecha = fecha;
    this.nombreUsuario = nombreUsuario;
    this.idRevista = idRevista;
  }
}
