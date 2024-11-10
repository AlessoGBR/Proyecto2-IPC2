export class Usuario {

    tipoUsuario: string;
    nombreUsuario: string;
    password: string;
    descripcion: string;
    foto: string;

    constructor(
        tipoUsuario: string,
        nombreUsuario: string,
        password: string,
        descripcion: string,
        foto: string
    ) {
        this.tipoUsuario = tipoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.descripcion = descripcion;
        this.foto = foto;
    }
}