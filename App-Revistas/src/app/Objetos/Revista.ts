import { Etiqueta } from "./Etiqueta";

export class Revista {

    idRevista?: number;
    revistaPath?: string;
    titulo: string;
    descripcion: string;
    version: string;
    suscripciones: boolean;
    tieneComentarios: boolean;
    tieneReacciones: boolean;
    tieneAnuncios: boolean;
    fecha: string;
    usuario: string| null= null;
    etiquetas: Etiqueta[];
    aprobada: boolean;
    denegada: boolean;
    precio: number;

    constructor(
        
        titu: string,
        descrip: string,
        version: string,        
        suscrip: boolean,
        tiene_comentario: boolean,
        tiene_reaccion: boolean,   
        tiene_anuncios: boolean,
        fech: string,     
        usuario: string| null= null,
        etiquet: Etiqueta[],
        aprobada: boolean = false,
        dengada: boolean= false,
        precio: number,
        idRevista?: number,
        revistaPath?: string
    )  {
        this.titulo = titu;
        this.descripcion = descrip;
        this.version = version;        
        this.suscripciones = suscrip;
        this.tieneComentarios = tiene_comentario;
        this.tieneReacciones = tiene_reaccion;
        this.tieneAnuncios = tiene_anuncios;
        this.fecha = fech;
        this.usuario = usuario;        
        this.etiquetas = etiquet;
        this.aprobada = aprobada;
        this.denegada = dengada;
        this.precio = precio;

        if (idRevista !== undefined) {
            this.idRevista = idRevista;
        }

        if (revistaPath !== undefined) {
            this.revistaPath = revistaPath;
        }
    }

    
}