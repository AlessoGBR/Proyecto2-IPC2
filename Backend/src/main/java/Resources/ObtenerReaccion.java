/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.ObtenerReacciones;
import Backend.Respuesta;
import Models.Reaccion;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author alesso
 */
@Path("Reacciones")
public class ObtenerReaccion {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarReaccion(Reaccion reaccion) {

        ObtenerReacciones reacciones = new ObtenerReacciones();
        boolean verificar = reacciones.verificarMegusta(reaccion.getIdRevista());
        if (verificar) {
            reacciones.darMG(reaccion);
            String mensaje = "si";
            Respuesta respuesta = new Respuesta(mensaje);
            return Response.status(Response.Status.OK).entity(respuesta).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Path("/ObtenerReacciones/{idRevista}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReaccion(@PathParam("idRevista") int idRevista) {

        ObtenerReacciones reacciones = new ObtenerReacciones();
        List<Reaccion> reaccion = reacciones.obtenerReaccionesPorRevista(idRevista);
        return Response.ok(reaccion).build();

    }

}
