export class Comentario {
  constructor(
    public idRevista: number,
    public comentario: string,
    public nombreUsuario: string,
    public fecha?: string
  ) {}
}
