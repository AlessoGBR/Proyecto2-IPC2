/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.ActualizarRevista;
import Backend.DB.CrearRevista;
import Backend.Respuesta;
import Models.Revista;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

/**
 *
 * @author alesso
 */
@Path("RegistroRevista")
public class RegistroRevista {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarRevista(Revista revista) {

        if (revista == null
                || revista.getTitulo() == null || revista.getFecha() == null
                || revista.getVersion() == null || revista.getDescripcion() == null) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta("Error en el registro: datos incompletos"))
                    .build();
        }
        boolean registroExitoso = new CrearRevista(revista).crearRevista();
        if (registroExitoso) {
            return Response.status(Response.Status.CREATED)
                    .entity(new Respuesta("Registro exitoso"))
                    .build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new Respuesta("Error en el registro: revista ya existente"))
                    .build();
        }
    }

    @PUT
    @Path("/actualizarComentarios/{idRevista}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarComentarios(@PathParam("idRevista") int idRevista, Map<String, Object> datos) {
        boolean comentariosActivos = (boolean) datos.get("tieneComentarios");
        boolean actualizado = new ActualizarRevista().actualizarEstadoComentarios(idRevista, comentariosActivos);
        
        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @PUT
    @Path("/actualizarMeGusta/{idRevista}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarMeGusta(@PathParam("idRevista") int idRevista, Map<String, Object> datos) {
        boolean megustaActivos = (boolean) datos.get("tieneMeGusta");
        boolean actualizado = new ActualizarRevista().actualizarEstadoMeGusta(idRevista, megustaActivos);
        
        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @PUT
    @Path("/actualizarSuscripciones/{idRevista}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarSuscripciones(@PathParam("idRevista") int idRevista, Map<String, Object> datos) {
        boolean megustaActivos = (boolean) datos.get("tieneSuscripciones");
        boolean actualizado = new ActualizarRevista().actualizarEstadoSuscripciones(idRevista, megustaActivos);
        
        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @PUT
    @Path("/actualizarAprobada/{idRevista}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarAceptada(@PathParam("idRevista") int idRevista) {
        
        boolean actualizado = new ActualizarRevista().actualizarAceptacion(idRevista);
        
        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @PUT
    @Path("/actualizarDenegada/{idRevista}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarDenegada(@PathParam("idRevista") int idRevista) {
        boolean actualizado = new ActualizarRevista().actualizarDenegada(idRevista);
        
        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
