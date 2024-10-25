import { Etiqueta } from "./Etiqueta";

export class Revista {

    idRevista?: number;
    titulo: string;
    descripcion: string;
    version: string;
    suscripciones: boolean;
    tieneComentarios: boolean;
    tieneReacciones: boolean;
    fecha: string;
    usuario: string| null= null;
    etiquetas: Etiqueta[];
    aprobada: boolean;
    denegada: boolean;

    constructor(
        
        titu: string,
        descrip: string,
        version: string,        
        suscrip: boolean,
        tiene_comentario: boolean,
        tiene_reaccion: boolean,   
        fech: string,     
        usuario: string| null= null,
        etiquet: Etiqueta[],
        aprobada: boolean = false,
        dengada: boolean= false,
        idRevista?: number,
    )  {
        this.titulo = titu;
        this.descripcion = descrip;
        this.version = version;        
        this.suscripciones = suscrip;
        this.tieneComentarios = tiene_comentario;
        this.tieneReacciones = tiene_reaccion;
        this.fecha = fech;
        this.usuario = usuario;        
        this.etiquetas = etiquet;
        this.aprobada = aprobada;
        this.denegada = dengada;

        if (idRevista !== undefined) {
            this.idRevista = idRevista;
        }
    }

    
}