export class Anuncio {

    idAnuncio: number;          
    tipo: string;               
    texto: string;              
    urlVideo: string;           
    pathImagen: string;         
    activo: boolean;            
    fechaInicio: Date;          
    fechaFinal: Date;           
    pago: number;               
    nombreAnunciante: string;   
    diasDuracion: number;       

    constructor(
        idAnuncio: number,
        tipo: string,
        texto: string,
        urlVideo: string,
        pathImagen: string,
        activo: boolean,
        fechaInicio: Date,
        fechaFinal: Date,
        pago: number,
        nombreAnunciante: string,
        diasDuracion: number
    ) {
        this.idAnuncio = idAnuncio;
        this.tipo = tipo;
        this.texto = texto;
        this.urlVideo = urlVideo;
        this.pathImagen = pathImagen;
        this.activo = activo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.pago = pago;
        this.nombreAnunciante = nombreAnunciante;
        this.diasDuracion = diasDuracion;
    }
}