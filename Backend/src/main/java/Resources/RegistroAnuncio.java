/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.CrearAnuncio;
import Backend.Respuesta;
import Models.Anuncio;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author alesso
 */
@Path("RegistroAnuncio")
public class RegistroAnuncio {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarAnuncio(Anuncio anuncioDTO) {
        boolean registroExitoso;

        try {
            Date fechaInicio = anuncioDTO.getFechaInicio();
            int dias = anuncioDTO.getDiasDuracion();
            long milliseconds = fechaInicio.getTime() + (dias * 24L * 60 * 60 * 1000); // Sumar días en milisegundos
            Date fechaFin = new Date(milliseconds);
            CrearAnuncio crearAnuncio = new CrearAnuncio();
            int id = crearAnuncio.obtenerIdAnunciantePorNombre(anuncioDTO.getNombreAnunciante());

            switch (anuncioDTO.getTipo()) {
                case "1":
                    crearAnuncio.crearAnuncioTexto(anuncioDTO.getTexto(), id, anuncioDTO.getFechaInicio(), fechaFin);
                    break;
                case "2":
                    crearAnuncio.crearAnuncioImagenYTexto(anuncioDTO.getTexto(), anuncioDTO.getPathImagen(), id, anuncioDTO.getFechaInicio(), fechaFin);
                    break;
                case "3":
                    crearAnuncio.crearAnuncioVideo(anuncioDTO.getUrlVideo(), id, anuncioDTO.getFechaInicio(), fechaFin);
                    break;
                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(new Respuesta("Tipo de anuncio no válido"))
                            .build();
            }
            registroExitoso = true;
        } catch (SQLException e) {
            registroExitoso = false;
            e.printStackTrace();
        }

        if (registroExitoso) {
            return Response.status(Response.Status.CREATED)
                    .entity(new Respuesta("Registro exitoso"))
                    .build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new Respuesta("Error en el registro"))
                    .build();
        }
    }

}
