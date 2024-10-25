export class Reaccion {

    constructor(
        public idRevista: number,
        public reaccion: boolean,
        public fecha: string,
        public nombreUsuario: string | null
      ) {}
      
}